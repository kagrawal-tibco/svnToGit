/**
 * 
 */
package com.tibco.be.ws.process;

import static com.tibco.be.ws.process.ProcessPropertyConstants.PROP_ID;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.BpmnModelCache;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.ontology.BpmnIndex;
import com.tibco.cep.bpmn.model.designtime.ontology.impl.DefaultBpmnIndex;
import com.tibco.cep.bpmn.model.designtime.utils.BpmnCommonModelUtils;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.model.designtime.utils.Identifier;
import com.tibco.cep.bpmn.model.designtime.utils.ROEObjectWrapper;
import com.tibco.xml.data.primitive.ExpandedName;

/**
 * @author vpatil
 */
public class CommonProcessUtil {
	public static final String BPMN_DOT_SEPARATOR = ".";
	
	public static EObjectWrapper<EClass, EObject> getFlowElementByType(String flowElementType) {
		EClass flowElementClass = null;
		
		if (BpmnModelClass.RULE_FUNCTION_TASK.getName().equals(flowElementType)) {
			flowElementClass = BpmnModelClass.RULE_FUNCTION_TASK;
		} else if (BpmnModelClass.START_EVENT.getName().equals(flowElementType)) {
			flowElementClass = BpmnModelClass.START_EVENT;
		} else if (BpmnModelClass.END_EVENT.getName().equals(flowElementType)) {
			flowElementClass = BpmnModelClass.END_EVENT;
		} else if (BpmnModelClass.INFERENCE_TASK.getName().equals(flowElementType)) {
			flowElementClass = BpmnModelClass.INFERENCE_TASK;
		} else if (BpmnModelClass.SEND_TASK.getName().equals(flowElementType)) {
			flowElementClass = BpmnModelClass.SEND_TASK;
		} else if (BpmnModelClass.RECEIVE_TASK.getName().equals(flowElementType)) {
			flowElementClass = BpmnModelClass.RECEIVE_TASK;
		} else if (BpmnModelClass.SERVICE_TASK.getName().equals(flowElementType)) {
			flowElementClass = BpmnModelClass.SERVICE_TASK;
		} else if (BpmnModelClass.BUSINESS_RULE_TASK.getName().equals(flowElementType)) {
			flowElementClass = BpmnModelClass.BUSINESS_RULE_TASK;
		} else if (BpmnModelClass.CALL_ACTIVITY.getName().equals(flowElementType)) {
			flowElementClass = BpmnModelClass.CALL_ACTIVITY;
		} else if (BpmnModelClass.SEQUENCE_FLOW.getName().equals(flowElementType)) {
			flowElementClass = BpmnModelClass.SEQUENCE_FLOW;
		} else if (BpmnModelClass.EXCLUSIVE_GATEWAY.getName().equals(flowElementType)) {
			flowElementClass = BpmnModelClass.EXCLUSIVE_GATEWAY;
		} else if (BpmnModelClass.PARALLEL_GATEWAY.getName().equals(flowElementType)) {
			flowElementClass = BpmnModelClass.PARALLEL_GATEWAY;
		} else if (BpmnModelClass.TEXT_ANNOTATION.getName().equals(flowElementType)) {
			flowElementClass = BpmnModelClass.TEXT_ANNOTATION;
		} else if (BpmnModelClass.ASSOCIATION.getName().equals(flowElementType)) {
			flowElementClass = BpmnModelClass.ASSOCIATION;
		} else {
			flowElementClass = BpmnModelClass.TASK;
		}
		
		EObjectWrapper<EClass, EObject> flowElementWrapper = EObjectWrapper.createInstance(flowElementClass);
		
		return flowElementWrapper;
	}
	
	public static String getFlowElementTypeByObject(EObjectWrapper<EClass, EObject> flowNodeWrapper) {
		String flowNodeType = null;
		
		if (flowNodeWrapper != null) {
			if (flowNodeWrapper.isInstanceOf(BpmnModelClass.RULE_FUNCTION_TASK)) {
				flowNodeType = BpmnModelClass.RULE_FUNCTION_TASK.getName();
			} else if (flowNodeWrapper.isInstanceOf(BpmnModelClass.INFERENCE_TASK)) {
				flowNodeType = BpmnModelClass.INFERENCE_TASK.getName();
			} else if (flowNodeWrapper.isInstanceOf(BpmnModelClass.START_EVENT)) {
				flowNodeType = BpmnModelClass.START_EVENT.getName();
			} else if (flowNodeWrapper.isInstanceOf(BpmnModelClass.END_EVENT)) {
				flowNodeType = BpmnModelClass.END_EVENT.getName();
			} else if (flowNodeWrapper.isInstanceOf(BpmnModelClass.SEND_TASK)) {
				flowNodeType = BpmnModelClass.SEND_TASK.getName();
			} else if (flowNodeWrapper.isInstanceOf(BpmnModelClass.RECEIVE_TASK)) {
				flowNodeType = BpmnModelClass.RECEIVE_TASK.getName();
			} else if (flowNodeWrapper.isInstanceOf(BpmnModelClass.SERVICE_TASK)) {
				flowNodeType = BpmnModelClass.SERVICE_TASK.getName();
			} else if (flowNodeWrapper.isInstanceOf(BpmnModelClass.BUSINESS_RULE_TASK)) {
				flowNodeType = BpmnModelClass.BUSINESS_RULE_TASK.getName();
			} else if (flowNodeWrapper.isInstanceOf(BpmnModelClass.CALL_ACTIVITY)) {
				flowNodeType = BpmnModelClass.CALL_ACTIVITY.getName();
			} else if (flowNodeWrapper.isInstanceOf(BpmnModelClass.PROCESS)) {
				flowNodeType = BpmnModelClass.PROCESS.getName();
			} else if (flowNodeWrapper.isInstanceOf(BpmnModelClass.JAVA_TASK)) {
				flowNodeType = BpmnModelClass.JAVA_TASK.getName();
			} else if (flowNodeWrapper.isInstanceOf(BpmnModelClass.SEQUENCE_FLOW)) {
				flowNodeType = BpmnModelClass.SEQUENCE_FLOW.getName();
			} else if (flowNodeWrapper.isInstanceOf(BpmnModelClass.EXCLUSIVE_GATEWAY)) {
				flowNodeType = BpmnModelClass.EXCLUSIVE_GATEWAY.getName();
			} else if (flowNodeWrapper.isInstanceOf(BpmnModelClass.PARALLEL_GATEWAY)) {
				flowNodeType = BpmnModelClass.PARALLEL_GATEWAY.getName();
			} else if (flowNodeWrapper.isInstanceOf(BpmnModelClass.TEXT_ANNOTATION)) {
				flowNodeType = BpmnModelClass.TEXT_ANNOTATION.getName();
			} else if (flowNodeWrapper.isInstanceOf(BpmnModelClass.ASSOCIATION)) {
				flowNodeType = BpmnModelClass.ASSOCIATION.getName();
			} else {
				flowNodeType = BpmnModelClass.TASK.getName();
			}
		}
		
		return flowNodeType;
	}
	
	public static List<EObject> getLanesById(EList<EObject> lanesets, List<String> laneIds) {
		List<EObject> selectedLanes = new ArrayList<EObject>();
		
		for (EObject laneSet : lanesets) {
			getLanesFromLaneSet(laneSet, laneIds, selectedLanes);
			if (selectedLanes.size() == laneIds.size()) {
				break;
			}
		}
		
		return selectedLanes;
	}
	
	private static void getLanesFromLaneSet(EObject laneSet, List<String> laneIds, List<EObject> selectedLanes) {
		EObjectWrapper<EClass, EObject> laneSetWrapper = EObjectWrapper.wrap(laneSet);
		
		EList<EObject> lanes = laneSetWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_LANES);
		if (lanes != null && lanes.size() > 0) {
			for (EObject lane : lanes) {
				EObjectWrapper<EClass, EObject> laneWrapper = EObjectWrapper.wrap(lane);
				String laneId = laneWrapper.getAttribute(PROP_ID);
				if (laneIds.contains(laneId)) {
					selectedLanes.add(lane);
				}
				
				if (laneWrapper.containsAttribute(BpmnMetaModelConstants.E_ATTR_CHILD_LANE_SET)) {
					EObjectWrapper<EClass, EObject> childLaneSet = laneWrapper
							.getWrappedEObjectAttribute(BpmnMetaModelConstants.E_ATTR_CHILD_LANE_SET);
					if (childLaneSet != null) {
						getLanesFromLaneSet(childLaneSet.getEInstance(), laneIds, selectedLanes);
					}
				}
			}
		}
	}
	
	public static String getInputMapperXslt(EObject userObject) {
		String xslt = "";
		if (userObject != null) {
			EObjectWrapper<EClass, EObject> userObjWrapper = EObjectWrapper
					.wrap(userObject);
			if (BpmnModelClass.ACTIVITY.isSuperTypeOf(userObject.eClass())) {
				List<EObject> dataInAssocs = userObjWrapper
						.getListAttribute(BpmnMetaModelConstants.E_ATTR_DATA_INPUT_ASSOCIATIONS);
				if (!dataInAssocs.isEmpty()) {
					ROEObjectWrapper<EClass, EObject> doAssocWrap = ROEObjectWrapper
							.wrap((EObject) dataInAssocs.get(0));

					EObject transform = (EObject) doAssocWrap
							.getAttribute(BpmnMetaModelConstants.E_ATTR_TRANSFORMATION);
					ROEObjectWrapper<EClass, EObject> transformWrap = ROEObjectWrapper
							.wrap(transform);
					xslt = (String) transformWrap
							.getAttribute(BpmnMetaModelConstants.E_ATTR_BODY);
				}
			} else if (BpmnModelClass.THROW_EVENT.isSuperTypeOf(userObject
					.eClass())) {
				List<EObject> dataInAssocs = userObjWrapper
						.getListAttribute(BpmnMetaModelConstants.E_ATTR_DATA_INPUT_ASSOCIATION);
				if (!dataInAssocs.isEmpty()) {
					ROEObjectWrapper<EClass, EObject> doAssocWrap = ROEObjectWrapper
							.wrap((EObject) dataInAssocs.get(0));

					EObject transform = (EObject) doAssocWrap
							.getAttribute(BpmnMetaModelConstants.E_ATTR_TRANSFORMATION);
					ROEObjectWrapper<EClass, EObject> transformWrap = ROEObjectWrapper
							.wrap(transform);
					xslt = (String) transformWrap
							.getAttribute(BpmnMetaModelConstants.E_ATTR_BODY);
				}
			}
		}
		return xslt;
	}
	
	public static void setIOMapperXslt(EObject userObject, String xslt, boolean isInput) {
		if (userObject != null) {
			String dataAssociationAttribute = null;
			EObjectWrapper<EClass, EObject> userObjWrapper = EObjectWrapper.wrap(userObject);
			
			if (BpmnModelClass.ACTIVITY.isSuperTypeOf(userObject.eClass())) {
				dataAssociationAttribute = (isInput)? BpmnMetaModelConstants.E_ATTR_DATA_INPUT_ASSOCIATIONS : BpmnMetaModelConstants.E_ATTR_DATA_OUTPUT_ASSOCIATIONS;
				
			} else if ( BpmnModelClass.THROW_EVENT.isSuperTypeOf(userObject.eClass()) || BpmnModelClass.CATCH_EVENT.isSuperTypeOf(userObject
					.eClass())) {
				dataAssociationAttribute = (isInput)? BpmnMetaModelConstants.E_ATTR_DATA_INPUT_ASSOCIATION : BpmnMetaModelConstants.E_ATTR_DATA_OUTPUT_ASSOCIATION;
			}
			
			EObjectWrapper<EClass, EObject> expressionWrapper = EObjectWrapper.createInstance(BpmnModelClass.FORMAL_EXPRESSION);
			expressionWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_BODY, xslt);
			
			EClass dataIOAssociation = (isInput)? BpmnModelClass.DATA_INPUT_ASSOCIATION : BpmnModelClass.DATA_OUTPUT_ASSOCIATION;
			EObjectWrapper<EClass, EObject> ioSpec = EObjectWrapper.createInstance(dataIOAssociation);
			ioSpec.setAttribute(BpmnMetaModelConstants.E_ATTR_TRANSFORMATION, expressionWrapper.getEInstance());
			
			List<EObject> associations = new ArrayList<EObject>();
			associations.add(ioSpec.getEInstance());
			
			userObjWrapper.setAttribute(dataAssociationAttribute, associations);
		}
	}
	
	public static String getOutputMapperXslt(EObject userObject) {
		String xslt = "";
		if (userObject != null) {
			EObjectWrapper<EClass, EObject> userObjWrapper = EObjectWrapper
					.wrap(userObject);
			if (BpmnModelClass.ACTIVITY.isSuperTypeOf(userObject.eClass())) {
				List<EObject> dataOutAssocs = userObjWrapper
						.getListAttribute(BpmnMetaModelConstants.E_ATTR_DATA_OUTPUT_ASSOCIATIONS);
				if (!dataOutAssocs.isEmpty()) {
					ROEObjectWrapper<EClass, EObject> doAssocWrap = ROEObjectWrapper
							.wrap((EObject) dataOutAssocs.get(0));

					EObject transform = (EObject) doAssocWrap
							.getAttribute(BpmnMetaModelConstants.E_ATTR_TRANSFORMATION);
					ROEObjectWrapper<EClass, EObject> transformWrap = ROEObjectWrapper
							.wrap(transform);
					xslt = (String) transformWrap
							.getAttribute(BpmnMetaModelConstants.E_ATTR_BODY);
				}
			} else if (BpmnModelClass.CATCH_EVENT.isSuperTypeOf(userObject
					.eClass())) {
				List<EObject> dataOutAssocs = userObjWrapper
						.getListAttribute(BpmnMetaModelConstants.E_ATTR_DATA_OUTPUT_ASSOCIATION);
				if (!dataOutAssocs.isEmpty()) {
					ROEObjectWrapper<EClass, EObject> doAssocWrap = ROEObjectWrapper
							.wrap((EObject) dataOutAssocs.get(0));

					EObject transform = (EObject) doAssocWrap
							.getAttribute(BpmnMetaModelConstants.E_ATTR_TRANSFORMATION);
					ROEObjectWrapper<EClass, EObject> transformWrap = ROEObjectWrapper
							.wrap(transform);
					xslt = (String) transformWrap
							.getAttribute(BpmnMetaModelConstants.E_ATTR_BODY);
				}
			}
		}
		return xslt;
	}
	
	public static EObjectWrapper<EClass, EObject> getFlowElementById(EObjectWrapper<EClass, EObject> processWrapper, String id) {
		List<EObject> allFlowNodes = BpmnCommonModelUtils.getAllFlowNodes(processWrapper.getEInstance());
		if (allFlowNodes != null && allFlowNodes.size() > 0) {
			for (EObject flowNode : allFlowNodes) {
				EObjectWrapper<EClass, EObject> flowNodeWrapper = EObjectWrapper.wrap(flowNode);
				String flowNodeId = flowNodeWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
				
				if (flowNodeId.equals(id)) {
					return flowNodeWrapper;
				}
			}
		}
		
		return null;
	}
	
	public static EObjectWrapper<EClass, EObject> getSequenceElementById(EObjectWrapper<EClass, EObject> processWrapper, String id) {
		List<EObject> allSequenceElement = BpmnCommonModelUtils.getAllSequenceFlows(processWrapper.getEInstance());
		if (allSequenceElement != null && allSequenceElement.size() > 0) {
			for (EObject sequenceNode : allSequenceElement) {
				EObjectWrapper<EClass, EObject> sequenceNodeWrapper = EObjectWrapper.wrap(sequenceNode);
				String sequenceNodeId = sequenceNodeWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
				
				if (sequenceNodeId.equals(id)) {
					return sequenceNodeWrapper;
				}
			}
		}
		
		return null;
	}
	
	public static EObjectWrapper<EClass, EObject> getTextAnnotationById(EObjectWrapper<EClass, EObject> processWrapper, String id) {
		Collection<EObject> allFlowNodes = BpmnCommonModelUtils.getArtifactNodes(processWrapper);
		if (allFlowNodes != null && allFlowNodes.size() > 0) {
			for (EObject flowNode : allFlowNodes) {
				EObjectWrapper<EClass, EObject> flowNodeWrapper = EObjectWrapper.wrap(flowNode);
				String flowNodeId = flowNodeWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
				
				if (flowNodeId.equals(id)) {
					return flowNodeWrapper;
				}
			}
		}
		
		return null;
	}
	
	public static String getPropertyFullPath(EObjectWrapper<EClass, EObject> itemDef) {
		String id = itemDef.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
		if(id == null)
			return null;
		id = id.replace("[]", "");
		ExpandedName itemDefinitionType = ExpandedName.parse(id);

		String strUriPath = "";
		String strConceptPath = "";
		String strUri = "www.tibco.com/be/ontology";
		if(itemDefinitionType != null && itemDefinitionType.getExpandedForm() != null) {
			strUriPath=itemDefinitionType.getExpandedForm().substring(1,(itemDefinitionType.getExpandedForm().lastIndexOf('}')));
			strConceptPath = strUriPath.substring(strUri.length(), strUriPath.length());
			return strConceptPath;
		}
		
		return null;
	}
	
	/**
	 * Validates the type and retrieves the Process EMF Wrapper
	 * 
	 * @param processWrapper
	 * @return
	 */
	public static EObjectWrapper<EClass, EObject> getProcessWrapper(Object processWrapper) {
		if (!(processWrapper instanceof EObjectWrapper)) {
			throw new IllegalArgumentException("Invalid argument. Argument should be a EObjectWrapper");
			} else {
			EObjectWrapper<EClass, EObject> processModelWrapper = (EObjectWrapper<EClass, EObject>) processWrapper;
				if (!(processModelWrapper.isInstanceOf(BpmnModelClass.PROCESS))) {
					throw new IllegalArgumentException("Invalid argument. Argument should be of type " + BpmnModelClass.PROCESS.toString());
				}
				return processModelWrapper;
			}
	}
	
	public static Identifier getNextIdentifier(EObject elementContainer,EClass type, String projectName, String name) {
		EObject index = BpmnModelCache.getInstance().getIndex(projectName);
		BpmnIndex ontology = new DefaultBpmnIndex(index);
		
		Identifier containerId = getFlowElementContainerId(elementContainer);
		
		Collection<EObject> elements;
		String identifierPrefix = null;
		if (type.equals(BpmnModelClass.PROCESS)) {
			elements = ontology.getAllProcesses();
			identifierPrefix = containerId.getId();
			List<Integer> noList = new ArrayList<Integer>();
			for (EObject element:elements) {
				EObjectWrapper<EClass, EObject> elementWrapper = EObjectWrapper.wrap(element);
				String id = elementWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
				if (id !=null) {
					if (id.startsWith(identifierPrefix)) {
						int lastIndex = id.lastIndexOf("_");
						if(lastIndex != -1) {
							String no = id.substring( lastIndex + 1);
							noList.add(getValidNo(no));
						}
					}
				}
			}
			if (noList.size() > 0) {
				int no = Collections.max(noList);
				no++;
				identifierPrefix = normaliseIdentifierPrefix(identifierPrefix);
				return new Identifier(identifierPrefix+"_"+ no);
			} else {
				identifierPrefix = normaliseIdentifierPrefix(identifierPrefix);
				return new Identifier(identifierPrefix);
			}
		} else {	
			elements = ontology.getProcessElementsByType(elementContainer, type);
			String typePrefix = null;
			if(name != null) {
				typePrefix = name;
			} else {
				typePrefix = type.getName();
			}
			typePrefix = normaliseIdentifierPrefix(typePrefix);
			identifierPrefix = containerId.getId()+"."+typePrefix;
			List<Integer> noList = new ArrayList<Integer>(); 
			for (EObject element:elements) {
				EObjectWrapper<EClass, EObject> elementWrapper = EObjectWrapper.wrap(element);
				String id = elementWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
				if (id !=null) {
					if (id.startsWith(identifierPrefix)) {
						int lastIndex = id.lastIndexOf("_");
						if(lastIndex != -1) {
							String no = id.substring(id.lastIndexOf("_") + 1);
							noList.add(getValidNo(no));
						}
					}
				}
			}
			if (noList.size() > 0) {
				int no = Collections.max(noList);
				no++;
				identifierPrefix = normaliseIdentifierPrefix(identifierPrefix);
				return new Identifier(identifierPrefix +"_"+ no);
			}
		}
		
		identifierPrefix = normaliseIdentifierPrefix(identifierPrefix);
		return new Identifier(identifierPrefix+"_"+0);
	}
	
	public static int getValidNo(String no) {
		int n;
		try{
			n = Integer.parseInt(no); 
		}catch(Exception e) {
			return 0;
		}
		return n;
	}
	
	private static String normaliseIdentifierPrefix(String identifierPrefix){
		if(identifierPrefix != null){
			identifierPrefix = identifierPrefix.trim();
			identifierPrefix = identifierPrefix.replaceAll("\\s+", "_");
		}
		
		return identifierPrefix;
	}
	
	/**
	 * Unescape XML data
	 * 
	 * @param xmlData
	 * @return
	 */
	public static String unescapeXml(String xmlData) {
		xmlData = StringEscapeUtils.unescapeXml(xmlData);
		xmlData = xmlData.replace("\"", "\\\"");
		xmlData = xmlData.replace("\n", "\\n");
		
		return xmlData;
	}
	
	private static Identifier getFlowElementContainerId(EObject flowElement) {
		if(!BpmnModelClass.FLOW_ELEMENTS_CONTAINER.isSuperTypeOf(flowElement.eClass())) {
			throw new IllegalArgumentException("Invalid flow element object.");
		}
		String s = null;
		EObjectWrapper<EClass, EObject> feWrapper = EObjectWrapper.wrap(flowElement);
		Identifier cname = new Identifier((String)feWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_ID));
		EObject parentContainer = flowElement.eContainer();
		if(parentContainer == null || 
				!BpmnModelClass.FLOW_ELEMENTS_CONTAINER.isSuperTypeOf(parentContainer.eClass())) {
			s = cname.getName();
		} else {
			s = getFlowElementContainerId(parentContainer).getId()+BPMN_DOT_SEPARATOR+cname.getName();
		}
		return new Identifier(s);
	}
}
