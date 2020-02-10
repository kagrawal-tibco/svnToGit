/**
 * 
 */
package com.tibco.be.ws.process;

import static com.tibco.be.ws.process.ProcessPropertyConstants.PROP_BINDINGTYPE;
import static com.tibco.be.ws.process.ProcessPropertyConstants.PROP_CONSUME;
import static com.tibco.be.ws.process.ProcessPropertyConstants.PROP_DEFAULT_SEQUENCEID;
import static com.tibco.be.ws.process.ProcessPropertyConstants.PROP_DOC_ID;
import static com.tibco.be.ws.process.ProcessPropertyConstants.PROP_DOC_TEXT;
import static com.tibco.be.ws.process.ProcessPropertyConstants.PROP_ENDPOINTURL;
import static com.tibco.be.ws.process.ProcessPropertyConstants.PROP_FORK_RULE_FUNCTION;
import static com.tibco.be.ws.process.ProcessPropertyConstants.PROP_ID;
import static com.tibco.be.ws.process.ProcessPropertyConstants.PROP_JNDICONTEXTURL;
import static com.tibco.be.ws.process.ProcessPropertyConstants.PROP_JOIN_RULE_FUNCTION;
import static com.tibco.be.ws.process.ProcessPropertyConstants.PROP_LANEIDS;
import static com.tibco.be.ws.process.ProcessPropertyConstants.PROP_LC_BEHAVIOR;
import static com.tibco.be.ws.process.ProcessPropertyConstants.PROP_LC_CARDINALITYBODY;
import static com.tibco.be.ws.process.ProcessPropertyConstants.PROP_LC_CONDITIONBODY;
import static com.tibco.be.ws.process.ProcessPropertyConstants.PROP_LC_ISSEQUENTIAL;
import static com.tibco.be.ws.process.ProcessPropertyConstants.PROP_LC_LOOPCONDITION;
import static com.tibco.be.ws.process.ProcessPropertyConstants.PROP_LC_LOOPCOUNT;
import static com.tibco.be.ws.process.ProcessPropertyConstants.PROP_LC_TESTBEFORE;
import static com.tibco.be.ws.process.ProcessPropertyConstants.PROP_MERGE_EXPRESSION;
import static com.tibco.be.ws.process.ProcessPropertyConstants.PROP_NAME;
import static com.tibco.be.ws.process.ProcessPropertyConstants.PROP_OPERATION;
import static com.tibco.be.ws.process.ProcessPropertyConstants.PROP_POINT_X;
import static com.tibco.be.ws.process.ProcessPropertyConstants.PROP_POINT_Y;
import static com.tibco.be.ws.process.ProcessPropertyConstants.PROP_PORT;
import static com.tibco.be.ws.process.ProcessPropertyConstants.PROP_REPLYTO;
import static com.tibco.be.ws.process.ProcessPropertyConstants.PROP_RESOURCE;
import static com.tibco.be.ws.process.ProcessPropertyConstants.PROP_SEQUENCEIDS;
import static com.tibco.be.ws.process.ProcessPropertyConstants.PROP_SERVICE;
import static com.tibco.be.ws.process.ProcessPropertyConstants.PROP_SOAPACTION;
import static com.tibco.be.ws.process.ProcessPropertyConstants.PROP_TIMEOUT;
import static com.tibco.be.ws.process.ProcessPropertyConstants.PROP_TIMEOUT_EVENT;
import static com.tibco.be.ws.process.ProcessPropertyConstants.PROP_TIMEOUT_EXPRESSION;
import static com.tibco.be.ws.process.ProcessPropertyConstants.PROP_TIMEOUT_UNIT;
import static com.tibco.be.ws.process.ProcessPropertyConstants.PROP_TRANSFORMATION;
import static com.tibco.be.ws.process.ProcessPropertyConstants.PROP_TYPE;
import static com.tibco.be.ws.process.ProcessPropertyConstants.PROP_UNIQUEID;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.ERROR_EVENT_DEFINITION;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.MESSAGE_EVENT_DEFINITION;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.SIGNAL_EVENT_DEFINITION;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.TIMER_EVENT_DEFINITION;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModel;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtension;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.BpmnCommonModelUtils;
import com.tibco.cep.bpmn.model.designtime.utils.EEnumWrapper;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.model.designtime.utils.Identifier;
import com.tibco.cep.bpmn.model.designtime.utils.ROEObjectWrapper;
import com.tibco.xml.data.primitive.ExpandedName;

/**
 * API's to interact with Process Flow nodes
 * 
 * @author vpatil
 */
public class FlowNodeHelper {
	public static final String BPMN_EXPRESSION_LANGUAGE_XPATH = "xpath";
	
	/**
	 * Retrieve the Lane details for the sequences
	 * 
	 * @param sequenceObject
	 * @return
	 */
	public static Map<String, Object> getLanes(EObject object) {
		Map<String, Object> sequenceLaneDetails = new HashMap<String,Object>();
		
		EObjectWrapper<EClass, EObject> wrapperObject = EObjectWrapper.wrap(object);
		if (wrapperObject.containsAttribute(BpmnMetaModelConstants.E_ATTR_LANES)) {
			EList<EObject> lanes = wrapperObject.getAttribute(BpmnMetaModelConstants.E_ATTR_LANES);
			String laneIds = "";
			for(EObject lane : lanes) {
				EObjectWrapper<EClass, EObject> laneObject = EObjectWrapper.wrap(lane);
				String laneId = laneObject.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
				laneIds += laneId + ",";
				String laneName = laneObject.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
				// NOTE - Not adding Name for now, Id's should be enough
			}
			if (!laneIds.isEmpty()) {
				laneIds = laneIds.substring(0, laneIds.length() - 1);
				sequenceLaneDetails.put(PROP_LANEIDS, laneIds);
			}
		}
		
		return sequenceLaneDetails;
	}
	
	/**
	 * Retrieves all the flow nodes in the given process
	 * 
	 * @param processObjWrapper
	 * @return
	 */
	public static EObject[] getAllFlowNodes(EObjectWrapper<EClass, EObject> processObjWrapper) {
		List<EObject> allFlowNodes = BpmnCommonModelUtils.getAllFlowNodes(processObjWrapper.getEInstance());
		if (allFlowNodes != null && allFlowNodes.size() > 0) {
			return allFlowNodes.toArray(new EObject[allFlowNodes.size()]);
		}
		return null;
	}
	
	/**
	 * Retrieves all the Text Annotations in the given process
	 * 
	 * @param processObjWrapper
	 * @return
	 */
	public static EObject[] getAllTextAnnotations(EObjectWrapper<EClass, EObject> processObjWrapper) {
		Collection<EObject> allTextAnnotations = BpmnCommonModelUtils.getArtifactNodes(processObjWrapper);
		if (allTextAnnotations != null && allTextAnnotations.size() > 0) {
			return allTextAnnotations.toArray(new EObject[allTextAnnotations.size()]);
		}
		return null;
	}
	
	/**
	 * Retrieve Base Flow Node Details
	 * 
	 * @param sequenceObject
	 * @return
	 */
	public static Map<String, Object> getFlowNodeDetails(EObject flowNode) {
		Map<String, Object> flowNodeDetails = new HashMap<String,Object>();
		
		EObjectWrapper<EClass, EObject> flowNodeWrapper = EObjectWrapper.wrap(flowNode);
		if (flowNodeWrapper.containsAttribute(BpmnMetaModelConstants.E_ATTR_NAME)) {
			String flowNodeName = flowNodeWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
			flowNodeDetails.put(PROP_NAME, flowNodeName);
		}
		
		String locationId = flowNodeWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
		flowNodeDetails.put(PROP_ID, locationId);
		
		int unique_id = flowNodeWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_UNIQUE_ID);
		flowNodeDetails.put(PROP_UNIQUEID, unique_id);
		
		Object resReferenced = BpmnCommonModelUtils.getAttachedResource(flowNode);
		if (resReferenced != null) {
			if (resReferenced instanceof Collection) {
				Collection<String> rules = (Collection<String>) resReferenced;
				
				String rulePaths = "";
				for (String rule : rules) {
					rulePaths += rule + ",";
				}
				if (rulePaths != null && ! rulePaths.isEmpty()) {
					rulePaths = rulePaths.substring(0, rulePaths.length() - 1);
					flowNodeDetails.put(PROP_RESOURCE, rulePaths);
				}
			} else {
				flowNodeDetails.put(PROP_RESOURCE, resReferenced);
			}
		}
		
		return flowNodeDetails;
	}
	
	/**
	 * Retrieve Text annotation value
	 * 
	 * @param textAnnotationNode
	 * @return
	 */
	public static String getTextAnnotationValue(EObject textAnnotationNode) {
		EObjectWrapper<EClass, EObject> textAnnotationWrapper = EObjectWrapper.wrap(textAnnotationNode);
		if (textAnnotationWrapper.containsAttribute(BpmnMetaModelConstants.E_ATTR_TEXT)) {
			String textValue = textAnnotationWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_TEXT);
			return textValue;		
		}
		return null;
	}
	
	/**
	 * Retrieve Flow Node Label
	 * 
	 * @param flowNode
	 * @return
	 */
	public static String getFlowNodeLabel(EObject flowNode) {
		EObjectWrapper<EClass, EObject> flowNodeWrapper = EObjectWrapper.wrap(flowNode);
		EObjectWrapper<EClass, EObject> flowNodeExtensionValueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(flowNodeWrapper);
		if (ExtensionHelper.isValidDataExtensionAttribute(flowNodeWrapper, BpmnMetaModelExtensionConstants.E_ATTR_LABEL)) {
			String label = flowNodeExtensionValueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_LABEL);
			return label;
		}
		return null;
	}
	
	/**
	 * Retrieve Flow Node Version
	 * 
	 * @param flowNode
	 * @return
	 */
	public static int getFlowNodeVersion(EObject flowNode) {
		EObjectWrapper<EClass, EObject> flowNodeWrapper = EObjectWrapper.wrap(flowNode);
		EObjectWrapper<EClass, EObject> flowNodeExtensionValueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(flowNodeWrapper);
		if (ExtensionHelper.isValidDataExtensionAttribute(flowNodeWrapper, BpmnMetaModelExtensionConstants.E_ATTR_VERSION)) {
			int version = flowNodeExtensionValueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_VERSION);
			return version;
		}
		
		return 0;
	}
	
	/**
	 * Retrieve Flow Node tool Id
	 * 
	 * @param flowNode
	 * @return
	 */
	public static String getFlowNodeToolId(EObject flowNode) {
		EObjectWrapper<EClass, EObject> flowNodeWrapper = EObjectWrapper.wrap(flowNode);
		EObjectWrapper<EClass, EObject> flowNodeExtensionValueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(flowNodeWrapper);
		if (ExtensionHelper.isValidDataExtensionAttribute(flowNodeWrapper, BpmnMetaModelExtensionConstants.E_ATTR_TOOL_ID)) {
			String toolId = flowNodeExtensionValueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_TOOL_ID);
			return toolId;
		}
		
		return null;
	}
	
	/**
	 * Retrieve Flow Node Check point value
	 * 
	 * @param flowNode
	 * @return
	 */
	public static boolean getFlowNodeCheckPoint(EObject flowNode) {
		EObjectWrapper<EClass, EObject> flowNodeWrapper = EObjectWrapper.wrap(flowNode);
		EObjectWrapper<EClass, EObject> flowNodeExtensionValueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(flowNodeWrapper);
		if (ExtensionHelper.isValidDataExtensionAttribute(flowNodeWrapper, BpmnMetaModelExtensionConstants.E_ATTR_CHECK_POINT)) {
			boolean checkPoint = flowNodeExtensionValueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_CHECK_POINT);
			return checkPoint;
		}
		
		return false;
	}
	
	/**
	 * Retrieve the sequence Id list associated to the flow node
	 * 
	 * @param flowNode
	 * @param isIncoming
	 * @return
	 */
	public static Map<String, Object> getFlowNodeSequences(EObject flowNode, boolean isIncoming) {
		Map<String, Object> flowNodeSequenceDetails = new HashMap<String,Object>();
		
		String sequenceType = (isIncoming) ? BpmnMetaModelConstants.E_ATTR_INCOMING : BpmnMetaModelConstants.E_ATTR_OUTGOING;
		EObjectWrapper<EClass, EObject> flowNodeWrapper = EObjectWrapper.wrap(flowNode);
		if (flowNodeWrapper.containsAttribute(sequenceType)) {
			List<EObject> sequences = flowNodeWrapper.getAttribute(sequenceType);

			String sequenceIds = "";
			for (EObject eObject : sequences) {
				EObjectWrapper<EClass, EObject> sequenceWrapperObject = EObjectWrapper.wrap(eObject);
				// NOTE - Not appending name, Id's should be enough
				String seqName = sequenceWrapperObject.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
				String seqLocationID = sequenceWrapperObject.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
				sequenceIds += seqLocationID + ",";
			}
			if (!sequenceIds.isEmpty()) {
				sequenceIds = sequenceIds.substring(0, sequenceIds.length() - 1);
				flowNodeSequenceDetails.put(PROP_SEQUENCEIDS, sequenceIds);
			}
		}
		
		return flowNodeSequenceDetails;
	}
	
	/**
	 * Get the X & Y points for Label and Node for the give Flow node
	 * 
	 * @param flowNodeObject
	 * @param isNodePoint
	 * @return
	 */
	public static Map<String, Object> getFlowNodePoint(EObject flowNodeObject, boolean isNodePoint) {
		Map<String, Object> sequencePointDetails = new HashMap<String,Object>();
		
		EObjectWrapper<EClass, EObject> flowNodeWrapperObject = EObjectWrapper.wrap(flowNodeObject);
		EObjectWrapper<EClass, EObject> flowNodeExtensionValueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(flowNodeWrapperObject);
		String point = (isNodePoint) ? BpmnMetaModelExtensionConstants.E_ATTR_NODE_POINT : BpmnMetaModelExtensionConstants.E_ATTR_LABEL_POINT;
		
		if (flowNodeExtensionValueWrapper.containsAttribute(point)) {
			EObject pointObject = flowNodeExtensionValueWrapper.getAttribute((point));
			if (point != null) {
				EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(pointObject);
				Double x = wrap.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_X);
				sequencePointDetails.put(PROP_POINT_X, x);
				Double y = wrap.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_Y);
				sequencePointDetails.put(PROP_POINT_Y, y);
			}
		}
		return sequencePointDetails;
	}
	
	/**
	 * Retrieve the XSLT input mapper for the flow node
	 * 
	 * @param flowNodeObject
	 * @return
	 */
	public static String getFlowNodeInputMapperXslt(EObject flowNodeObject) {
		return CommonProcessUtil.getInputMapperXslt(flowNodeObject);
	}
	
	/**
	 * Retrieve the XSLT output mapper for the flow node
	 * 
	 * @param flowNodeObject
	 * @return
	 */
	public static String getFlowNodeOutputMapperXslt(EObject flowNodeObject) {
		return CommonProcessUtil.getOutputMapperXslt(flowNodeObject);
	}
	
	/**
	 * Check if Timer is enabled
	 * 
	 * @param flowNodeObject
	 * @return
	 */
	public static boolean isTimerEnabled(EObject flowNodeObject) {
		if (flowNodeObject != null) {
			EObjectWrapper<EClass, EObject> wrapper = EObjectWrapper
					.wrap(flowNodeObject);
			EObjectWrapper<EClass, EObject> flowNodeExtensionValueWrapper = ExtensionHelper
					.getAddDataExtensionValueWrapper(wrapper);
			if (flowNodeExtensionValueWrapper.containsAttribute(BpmnMetaModelExtensionConstants.E_ATTR_TIMER_ENABLED)) {
				Boolean timerEnabled = flowNodeExtensionValueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_TIMER_ENABLED);
				return timerEnabled;
			}
		}
		return false;
	}
	
	/**
	 * Retrieve the timer details if its enabled
	 * 
	 * @param flowNodeObject
	 * @return
	 */
	public static Map<String, Object> getTimerDetails(EObject flowNodeObject) {
		Map<String, Object> timerDetails = new HashMap<String,Object>();

		EObjectWrapper<EClass, EObject> flowNodeWrapperObject = EObjectWrapper.wrap(flowNodeObject);
		EObjectWrapper<EClass, EObject> flowNodeExtensionValueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(flowNodeWrapperObject);
		
		EObject timeoutData = flowNodeExtensionValueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_TIMER_DATA);
		if(timeoutData != null) {
			EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(timeoutData);
			String xpathExpr = wrap.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_EXPRESSION);
			timerDetails.put(PROP_TIMEOUT_EXPRESSION, xpathExpr);
			String timeoutEvent = wrap.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_EVENT);
			timerDetails.put(PROP_TIMEOUT_EVENT, timeoutEvent);
			EEnumLiteral unitEnum = wrap.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_UNIT);
			String timeoutUnit = unitEnum.getLiteral();
			timerDetails.put(PROP_TIMEOUT_UNIT,timeoutUnit);
		}

		return timerDetails;
	}
	
	/**
	 * Retrieve loop characteristics if any
	 * 
	 * @param flowNodeObject
	 * @return
	 */
	public static EObject getLoopCharacteristics(EObject flowNodeObject) {
		EObjectWrapper<EClass, EObject> flowNodeWrapper = EObjectWrapper.wrap(flowNodeObject);
	
		if (flowNodeWrapper.containsAttribute(BpmnMetaModelConstants.E_ATTR_LOOP_CHARACTERISTICS)) {
			EObject loopCharacteristics = flowNodeWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_LOOP_CHARACTERISTICS);
			return loopCharacteristics;
		}
		return null;
	}
	
	/**
	 * Retrieves loop characteristics details
	 * 
	 * @param flowNodeObject
	 * @return
	 */
	public static Map<String, Object> getLoopCharacteristicDetails(EObject loopCharacteristics) {
		Map<String, Object> loopCharacteristicDetails = new HashMap<String,Object>();

		EObjectWrapper<EClass, EObject> loopCharacteristicsWrapper = EObjectWrapper.wrap(loopCharacteristics);
		
		if (loopCharacteristicsWrapper.isInstanceOf(BpmnModelClass.STANDARD_LOOP_CHARACTERISTICS)) {
			loopCharacteristicDetails.put(PROP_TYPE, BpmnModelClass.STANDARD_LOOP_CHARACTERISTICS.getName());
			
			boolean testBefore = (Boolean)loopCharacteristicsWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_TEST_BEFORE);
			loopCharacteristicDetails.put(PROP_LC_TESTBEFORE, testBefore);
			
			EObject loopCondition = (EObject)loopCharacteristicsWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_LOOP_CONDITION);
			if (loopCondition != null) {
				EObjectWrapper<EClass, EObject> expressionWrapper = EObjectWrapper.wrap(loopCondition);
				String body =(String) expressionWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_BODY);
				loopCharacteristicDetails.put(PROP_LC_LOOPCONDITION, body);
			}
			
			String loopCount = (String)loopCharacteristicsWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_LOOP_MAXIMUM);
			loopCharacteristicDetails.put(PROP_LC_LOOPCOUNT, loopCount);
			
		} else if (loopCharacteristicsWrapper.isInstanceOf(BpmnModelClass.MULTI_INSTANCE_LOOP_CHARACTERISTICS)) {
			
			loopCharacteristicDetails.put(PROP_TYPE, BpmnModelClass.MULTI_INSTANCE_LOOP_CHARACTERISTICS.getName());
			
			boolean isSequential = (Boolean)loopCharacteristicsWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_IS_SEQUENTIAL);
			loopCharacteristicDetails.put(PROP_LC_ISSEQUENTIAL, isSequential);
			
			boolean testBefore = (Boolean)loopCharacteristicsWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_TEST_BEFORE);
			loopCharacteristicDetails.put(PROP_LC_TESTBEFORE, testBefore);
			
			String collections = loopCharacteristicsWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_ITERATOR_XSLT);
			if (null != collections) {
				loopCharacteristicDetails.put(PROP_LC_CARDINALITYBODY,collections);
			}
			
			EEnumLiteral bv = loopCharacteristicsWrapper.getEnumAttribute(BpmnMetaModelConstants.E_ATTR_BEHAVIOR);
			String behavior = bv.getLiteral();
			loopCharacteristicDetails.put(PROP_LC_BEHAVIOR, behavior);
			
			Object completionCondition = loopCharacteristicsWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_COMPLETION_CONDITION);
			if (completionCondition != null) {
					EObjectWrapper<EClass, EObject> completionWrapper = EObjectWrapper
							.wrap((EObject) completionCondition);
					String conditionBody = (String)completionWrapper
							.getAttribute(BpmnMetaModelConstants.E_ATTR_BODY);
					loopCharacteristicDetails.put(PROP_LC_CONDITIONBODY, conditionBody);
			}
		}
		
		
		return loopCharacteristicDetails;
	}
	
	/**
	 * Retrieve the type of Flow Node
	 * 
	 * @param flowNodeObject
	 * @return
	 */
	public static String getFlowNodeType(EObject flowNodeObject) {
		EObjectWrapper<EClass, EObject> flowNodeWrapper = EObjectWrapper.wrap(flowNodeObject);
		return CommonProcessUtil.getFlowElementTypeByObject(flowNodeWrapper);
	}
	
	/**
	 * Retrieve the Implementation URI's for a Business Rule Flow Node
	 * 
	 * @param flowNodeObject
	 * @return
	 */
	public static String[] getImplementationURIList(EObject flowNodeObject) {
		List<String> impls = new ArrayList<String>();
		if (flowNodeObject.eClass().equals(BpmnModelClass.BUSINESS_RULE_TASK)) {
			EObjectWrapper<EClass, EObject> wrap = EObjectWrapper
					.wrap(flowNodeObject);
			if (ExtensionHelper.isValidDataExtensionAttribute(wrap,
					BpmnMetaModelExtensionConstants.E_ATTR_IMPLEMENTATIONS)) {
				EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper
						.getAddDataExtensionValueWrapper(wrap);
				if (valueWrapper != null) {
					ArrayList<EObject> arrayList = new ArrayList<EObject>(
							valueWrapper
									.getListAttribute(BpmnMetaModelExtensionConstants.E_ATTR_IMPLEMENTATIONS));
					String item = null;
					for (EObject eObject : arrayList) {
						EObjectWrapper<EClass, EObject> implementation = EObjectWrapper
								.wrap(eObject);
						String uri = implementation
								.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_URI);
						if (uri != null && !uri.trim().isEmpty()) {
							item = uri;
						}
						if (implementation.containsAttribute(BpmnMetaModelExtensionConstants.E_ATTR_DEPLOYED)) {
							Boolean isDeployed = implementation.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_DEPLOYED);
							item += ":" + isDeployed.toString();
						}
						impls.add(item);
						
					}
				}
			}
		}
		
		return impls.toArray(new String[impls.size()]);
	}
	
	/**
	 * Retrieve Service Details for Web Service Flow Node
	 * 
	 * @param flowNodeObject
	 * @return
	 */
	public static Map<String, Object> getServiceDetails(EObject flowNodeObject) {
		Map<String, Object> serviceDetails = new HashMap<String,Object>();
		
		EObjectWrapper<EClass, EObject> flowNodeWrapper = EObjectWrapper.wrap(flowNodeObject);
		
		if(flowNodeWrapper.getEClassType().equals(BpmnModelClass.SERVICE_TASK)){
			EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper
					.getAddDataExtensionValueWrapper(flowNodeWrapper);
			if(valueWrapper != null){
				String service = valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_SERVICE);
				if(service != null && !service.trim().isEmpty())
					serviceDetails.put(PROP_SERVICE, service);
				String port = valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_PORT);
				if(port != null && !port.trim().isEmpty())
					serviceDetails.put(PROP_PORT, port);
				String operation = valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_OPERATION);
				if(operation != null && !operation.trim().isEmpty())
					serviceDetails.put(PROP_OPERATION, operation);
				String soapAction = valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_SOAP_ACTION);
				if(soapAction != null && !soapAction.trim().isEmpty())
					serviceDetails.put(PROP_SOAPACTION, soapAction);
				Long timeout = valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_TIMEOUT);
				if(timeout != null)
					serviceDetails.put(PROP_TIMEOUT, timeout);
				
				EEnumLiteral propType = valueWrapper
						.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_BINDING_TYPE);
				if (propType != null) {
					serviceDetails.put(PROP_BINDINGTYPE, propType.getLiteral());
				}
				if (propType == null || propType.equals(BpmnModelClass.ENUM_WS_BINDING_HTTP)){
					String endPointUrl = valueWrapper
							.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_END_POINT_URL);
					if(endPointUrl != null && !endPointUrl.trim().isEmpty())
						serviceDetails.put(PROP_ENDPOINTURL, endPointUrl);
				} else {
					String providerUrl = valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JNDI_PROVIDER_URL);
					if(providerUrl != null && !providerUrl.trim().isEmpty())
						serviceDetails.put(PROP_JNDICONTEXTURL, providerUrl);
				}
			}
		}
		
		return serviceDetails;
	}

	/**
	 * Retrieve the key Expression for Receive Message Flow Node
	 * 
	 * @param flowNodeObject
	 * @return
	 */
	public static String getKeyExpression(EObject flowNodeObject) {
		EObjectWrapper<EClass, EObject> flowNodeWrapper = EObjectWrapper.wrap(flowNodeObject);
		EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper
				.getAddDataExtensionValueWrapper(flowNodeWrapper);
		String keyExpression = (String) valueWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_JOB_KEY);
		
		return keyExpression;
	}
	
	/**
	 * Get the list of Message Starts for End Event
	 * 
	 * @param flowNodeObject
	 * @return
	 */
	public static EObject[] getMessageStarterList(EObject flowNodeObject) {
		if (flowNodeObject.eClass().equals(BpmnModelClass.END_EVENT)) {
			EObjectWrapper<EClass, EObject> flowNodeWrapper = EObjectWrapper
					.wrap(flowNodeObject);
			EObjectWrapper<EClass, EObject> addDataExtensionValueWrapper = ExtensionHelper
					.getAddDataExtensionValueWrapper(flowNodeWrapper);
			if (addDataExtensionValueWrapper
					.containsAttribute(BpmnMetaModelExtensionConstants.E_ATTR_MESSAGE_STARTERS)) {

				List<EObject> listAttribute = addDataExtensionValueWrapper
						.getListAttribute(BpmnMetaModelExtensionConstants.E_ATTR_MESSAGE_STARTERS);
				
				return listAttribute.toArray(new EObject[listAttribute.size()]);
			}
		}
		
		return null;
	}
	
	/**
	 * Retrieve the Message Starter Details
	 * 
	 * @param flowNodeObject
	 * @return
	 */
	public static Map<String, Object> getMessageStarterDetails(EObject listAttributeEObject) {
		Map<String, Object> msgStarterDetails = new HashMap<String, Object>();
		
		ROEObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(listAttributeEObject);
		// here id is start event/receive task flow node
		String id = wrap
				.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_MESSAGE_STARTER);
		msgStarterDetails.put(PROP_ID, id);
		boolean replyTo = (Boolean) wrap
				.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_REPLY_TO);
		msgStarterDetails.put(PROP_REPLYTO, replyTo);
		boolean consume = (Boolean) wrap
				.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_CONSUME);
		msgStarterDetails.put(PROP_CONSUME, consume); 
			
		return msgStarterDetails;
	}
	
	/**
	 * Retrieve priority for Start/End Event Nodes
	 * 
	 * @param flowNodeObject
	 * @return
	 */
	public static int getPriority(EObject flowNodeObject) {
		EObject flowNodeExtn = ExtensionHelper.getAddDataExtensionValue(flowNodeObject);
		EObjectWrapper<EClass, EObject> flowNodeExtnWrapper = EObjectWrapper.wrap(flowNodeExtn);
		
		if (flowNodeExtnWrapper.containsAttribute(BpmnMetaModelConstants.E_ATTR_PRIORITY)) {
			Integer priority = (Integer) flowNodeExtnWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_PRIORITY);
			return priority;
		}
		
		return -1;
	}
	
	/**
	 * Retrieve Gateway direction for Gateway flow node
	 * 
	 * @param flowNodeObject
	 * @return
	 */
	public static String getGatewayDirection(EObject flowNodeObject) {
		EObjectWrapper<EClass, EObject> flowNodeWrapper = EObjectWrapper.wrap(flowNodeObject);
		
		if (flowNodeWrapper.containsAttribute(BpmnMetaModelConstants.E_ATTR_GATEWAY_DIRECTION)) {
			EEnumLiteral gatewayDirection = flowNodeWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_GATEWAY_DIRECTION);
			return gatewayDirection.getLiteral();
		}
		
		return null;
	}
	
	/**
	 * Retrieve details of exclusive gateways
	 * 
	 * @param flowNodeObject
	 * @return
	 */
	public static Object getExclusiveGatewayDetails(EObject flowNodeObject) {
		Map<String, Object> exclusiveGatewayDetails = new HashMap<String, Object>();
		
		EObjectWrapper<EClass, EObject> flowNodeWrapper = EObjectWrapper.wrap(flowNodeObject);
		if (flowNodeWrapper.containsAttribute(BpmnMetaModelConstants.E_ATTR_DEFAULT)) {
			EObject defaultSeq = (EObject)flowNodeWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_DEFAULT);
			if (defaultSeq != null) {
				EObjectWrapper<EClass, EObject> sequenceWrapperObject = EObjectWrapper.wrap(defaultSeq);

				String locationId = sequenceWrapperObject.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
				exclusiveGatewayDetails.put(PROP_DEFAULT_SEQUENCEID, locationId);
			}
		}
		EObjectWrapper<EClass, EObject> flowNodeExtensionValueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(flowNodeWrapper);
		
		if (flowNodeExtensionValueWrapper.containsAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JOIN_EXPRESSIONS)) {
			EList<EObject> gatewayExpressionList = flowNodeExtensionValueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JOIN_EXPRESSIONS);
			for (EObject eObject : gatewayExpressionList) {
				EObjectWrapper<EClass, EObject> gatewayExpressionWrapperObject = EObjectWrapper.wrap(eObject);
				
				if (gatewayExpressionWrapperObject != null) {
					String transformation = gatewayExpressionWrapperObject.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_TRANSFORMATION);
					exclusiveGatewayDetails.put(PROP_TRANSFORMATION, transformation);

					String sequenceId = gatewayExpressionWrapperObject.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_SEQUENCE_FLOW_ID);
					exclusiveGatewayDetails.put(PROP_SEQUENCEIDS, sequenceId);
				}
			}
		}
		
		return exclusiveGatewayDetails;
	}
	
	/**
	 * Retrieves details of parallel gateways
	 * 
	 * @param flowNodeObject
	 * @return
	 */
	public static Object getParallelGatewayDetails(EObject flowNodeObject) {
		Map<String, Object> parallelGatewayDetails = new HashMap<String, Object>();
		
		EObjectWrapper<EClass, EObject> flowNodeWrapper = EObjectWrapper.wrap(flowNodeObject);
		EObjectWrapper<EClass, EObject> flowNodeExtensionValueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(flowNodeWrapper);
		
		if (flowNodeExtensionValueWrapper.containsAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JOIN_RULEFUNCTION)) {
			String joinRulefunction = flowNodeExtensionValueWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_JOIN_RULEFUNCTION);
			parallelGatewayDetails.put(PROP_JOIN_RULE_FUNCTION, joinRulefunction);
			
			String mergeExpression = (String) flowNodeExtensionValueWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_MERGE_EXPRESSION);
			parallelGatewayDetails.put(PROP_MERGE_EXPRESSION, mergeExpression);
		}
		
		if (flowNodeExtensionValueWrapper.containsAttribute(BpmnMetaModelExtensionConstants.E_ATTR_FORK_RULEFUNCTION)) {
			String forkRulefunction = flowNodeExtensionValueWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_FORK_RULEFUNCTION);
			parallelGatewayDetails.put(PROP_FORK_RULE_FUNCTION, forkRulefunction);
		}
		
		return parallelGatewayDetails;
	}
	
	/**
	 * Retrieves Documentation details
	 * 
	 * @param flowNodeObject
	 * @return
	 */
	public static Object getDocumentationDetails(EObject flowNodeObject) {
		Map<String, Object> documentationDetails = new HashMap<String, Object>();
		
		EObjectWrapper<EClass, EObject> flowNodeWrapper = EObjectWrapper.wrap(flowNodeObject);
		
		if (flowNodeWrapper.containsAttribute(BpmnMetaModelConstants.E_ATTR_DOCUMENTATION)) {
			EList<EObject> documentationList = flowNodeWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_DOCUMENTATION);
			for (EObject documentation : documentationList) {
				EObjectWrapper<EClass, EObject> documentationWrapperObject = EObjectWrapper.wrap(documentation);

				String docId = documentationWrapperObject.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
				documentationDetails.put(PROP_DOC_ID, docId);

				String docText = documentationWrapperObject.getAttribute(BpmnMetaModelConstants.E_ATTR_TEXT);
				documentationDetails.put(PROP_DOC_TEXT, docText);
			}
		}
		
		return documentationDetails;
	}
	
	/**
	 * Retrieves Node Event Definition
	 * 
	 * @param flowNodeObject
	 * @return
	 */
	public static Object getEventDefinitionDetails(EObject flowNodeObject) {
		Map<String, Object> eventDefinitionDetails = new HashMap<String, Object>();
		
		String eventDefinitionType = null;
		
		EObjectWrapper<EClass, EObject> flowNodeWrapper = EObjectWrapper.wrap(flowNodeObject);
		
		EList<EObject> eventDefinitions = flowNodeWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_EVENT_DEFINITIONS);
		if (eventDefinitions.size() == 0 ) {
			eventDefinitions = flowNodeWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_EVENT_DEFINITION_REFS);
		}
		
		EObject eDef = eventDefinitions.size()>  0 ? eventDefinitions.get(0): null;
		EClass extType =  null;
		if(eDef != null ){
			extType = eDef.eClass();
		}
		
		if (extType != null) {
			if (MESSAGE_EVENT_DEFINITION.isSuperTypeOf(extType)) eventDefinitionType = MESSAGE_EVENT_DEFINITION.getName();
			else if (TIMER_EVENT_DEFINITION.isSuperTypeOf(extType)) eventDefinitionType = TIMER_EVENT_DEFINITION.getName();
			else if (SIGNAL_EVENT_DEFINITION.isSuperTypeOf(extType)) eventDefinitionType = SIGNAL_EVENT_DEFINITION.getName();
			else if (ERROR_EVENT_DEFINITION.isSuperTypeOf(extType)) eventDefinitionType = ERROR_EVENT_DEFINITION.getName();
			
			eventDefinitionDetails.put(PROP_TYPE, eventDefinitionType);
		}
		
		
		if (eventDefinitions.size() > 0) {
			EObjectWrapper<EClass, EObject> eventDefinitionWrapper = EObjectWrapper.wrap(eventDefinitions.get(0));
			String eventDefinitionId = eventDefinitionWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
			
			eventDefinitionDetails.put(PROP_ID, eventDefinitionId);
		}
		
		return eventDefinitionDetails;
	}
	
	/**
	 * Set the appropriate event definition for the specified node
	 * 
	 * @param processObjWrapper
	 * @param flowElementWrapper
	 * @param eventDefinitionType
	 * @param eventDefId
	 */
	public static void setEventDefinitionType(EObjectWrapper<EClass, EObject> processObjWrapper, EObjectWrapper<EClass, EObject> flowElementWrapper, String eventDefinitionType, String eventDefId, String name) {
		EClass eventDefinition = null;
		
		if (eventDefinitionType != null && !eventDefinitionType.isEmpty()) {
			if (eventDefinitionType.equals(MESSAGE_EVENT_DEFINITION.getName())) eventDefinition = MESSAGE_EVENT_DEFINITION;
			else if (eventDefinitionType.equals(TIMER_EVENT_DEFINITION.getName())) eventDefinition = TIMER_EVENT_DEFINITION;
			else if (eventDefinitionType.equals(SIGNAL_EVENT_DEFINITION.getName())) eventDefinition = SIGNAL_EVENT_DEFINITION;
			else if (eventDefinitionType.equals(ERROR_EVENT_DEFINITION.getName())) eventDefinition = ERROR_EVENT_DEFINITION;
		
			EObjectWrapper<EClass, EObject> eventDefinitionWrapper = EObjectWrapper.createInstance(eventDefinition);

			if (eventDefId == null) {
				Identifier eventDefIdentifier = CommonProcessUtil.getNextIdentifier(processObjWrapper.getEInstance(), flowElementWrapper.getEClassType(), processObjWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_OWNER_PROJECT).toString(), name);
				eventDefId = eventDefIdentifier.getId();
			}

			eventDefinitionWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_ID, eventDefId);

			flowElementWrapper.addToListAttribute(BpmnMetaModelConstants.E_ATTR_EVENT_DEFINITIONS, eventDefinitionWrapper);
		}
	}
	
	/**
	 * Sets the documentation details
	 * 
	 * @param processObjWrapper
	 * @param flowElementWrapper
	 * @param docId
	 * @param docText
	 * @param name
	 */
	public static void setDocumentation(EObjectWrapper<EClass, EObject> processObjWrapper, EObjectWrapper<EClass, EObject> flowElementWrapper, String docId, String docText, String name) {
		if (docId == null) {
			Identifier flowElementIdentifier = CommonProcessUtil.getNextIdentifier(processObjWrapper.getEInstance(), flowElementWrapper.getEClassType(), processObjWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_OWNER_PROJECT).toString(), name);
			docId = flowElementIdentifier.getId();
		}
		
		EObjectWrapper<EClass, EObject> docWrapper = EObjectWrapper.createInstance(BpmnMetaModel.DOCUMENTATION);
		docWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_ID, docId);
		docWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_TEXT, docText);
		flowElementWrapper.addToListAttribute(BpmnMetaModelConstants.E_ATTR_DOCUMENTATION, docWrapper);
	}
	
	/**
	 * Set the priority value for a flow node, typically for start/end Event nodes
	 * 
	 * @param flowElementWrapper
	 * @param priority
	 */
	public static void setPriority(EObjectWrapper<EClass, EObject> flowElementWrapper, int priority) {
		EObjectWrapper<EClass, EObject> flowElementExtentionWrapper = ExtensionHelper
				.getAddDataExtensionValueWrapper(flowElementWrapper);
		if (flowElementExtentionWrapper != null && flowElementExtentionWrapper.containsAttribute(BpmnMetaModelConstants.E_ATTR_PRIORITY)) {
			flowElementExtentionWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_PRIORITY, priority);
		}
	}
	
	/**
	 * Create a new Flow node EMF Object
	 * 
	 * @param processObjWrapper
	 * @param flowElementType
	 * @param laneIds
	 * @return
	 */
	public static EObjectWrapper<EClass, EObject> createFlowNode(EObjectWrapper<EClass, EObject> processObjWrapper, String flowElementType, String[] laneIds) {
		EObjectWrapper<EClass, EObject> flowElementWrapper = CommonProcessUtil.getFlowElementByType(flowElementType);
		
		if (laneIds != null) {
			EList<EObject> laneSets = processObjWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_LANE_SETS);
			if (laneSets != null && laneSets.size() > 0) {
				List<EObject> lanes = CommonProcessUtil.getLanesById(laneSets, Arrays.asList(laneIds));
				for (EObject lane : lanes) {
					EObjectWrapper<EClass, EObject> laneWrapper = EObjectWrapper.wrap(lane);

					List<EObject> lanepath = BpmnCommonModelUtils.getLanePath(processObjWrapper, laneWrapper);
					for (EObject laneItem : lanepath) {
						flowElementWrapper.addToListAttribute(
								BpmnMetaModelConstants.E_ATTR_LANES, laneItem);
					}
					laneWrapper.addToListAttribute(
							BpmnMetaModelConstants.E_ATTR_FLOW_ELEMENT_REFS,
							flowElementWrapper.getEInstance());
				}
			}
		}
		
		processObjWrapper.addToListAttribute(BpmnMetaModelConstants.E_ATTR_FLOW_ELEMENTS, flowElementWrapper);
		
		return flowElementWrapper;
	}
	
	/**
	 * Create a new text annotation node EMF Object
	 * 
	 * @param processObjWrapper
	 * @param flowElementType
	 * @return
	 */
	public static EObjectWrapper<EClass, EObject> createTextAnnotationNode(EObjectWrapper<EClass, EObject> processObjWrapper, String flowElementType) {
		EObjectWrapper<EClass, EObject> flowElementWrapper = CommonProcessUtil.getFlowElementByType(flowElementType);
		processObjWrapper.addToListAttribute(BpmnMetaModelConstants.E_ATTR_ARTIFACTS, flowElementWrapper);
		
		return flowElementWrapper;
	}
	
	/**
	 * Set base attributes of the specified flow element
	 * 
	 * @param flowElementWrapper
	 * @param id
	 * @param uniqueId
	 * @param name
	 * @param resourcePaths
	 */
	public static void setFlowElementBaseAttributes(EObjectWrapper<EClass, EObject> processObjWrapper, EObjectWrapper<EClass, EObject> flowElementWrapper, int uniqueId, String name, String resourcePaths, String flowElementId) {
		if (flowElementId == null) {
			Identifier flowElementIdentifier = CommonProcessUtil.getNextIdentifier(processObjWrapper.getEInstance(), flowElementWrapper.getEClassType(), processObjWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_OWNER_PROJECT).toString(), name);
			flowElementId = flowElementIdentifier.getId();
		}
		flowElementWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_ID, flowElementId);
		if (flowElementWrapper.containsAttribute(BpmnMetaModelConstants.E_ATTR_NAME)) flowElementWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_NAME, name);
		flowElementWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_UNIQUE_ID, uniqueId);
		
		if (resourcePaths != null && !resourcePaths.isEmpty()) {
			Object entities = null;
			if (resourcePaths.contains(",")) {
				entities = Arrays.asList(resourcePaths.split(","));
			} else {
				entities = resourcePaths;
			}
			BpmnCommonModelUtils.setResourceAttr(flowElementWrapper.getEInstance(), entities);
		}
	}
	
	/**
	 * Set base extention attributes of the specified flow element
	 * 
	 * @param flowElementWrapper
	 * @param version
	 * @param label
	 * @param checkpoint
	 * @param toolId
	 */
	public static void setFlowElementBaseExtentionAttributes(EObjectWrapper<EClass, EObject> flowElementWrapper, int version, String label, boolean checkpoint, String toolId) {
		EObjectWrapper<EClass, EObject> flowElementExtentionWrapper = ExtensionHelper
				.getAddDataExtensionValueWrapper(flowElementWrapper);
		if (flowElementExtentionWrapper != null) {
			if (ExtensionHelper.isValidDataExtensionAttribute(flowElementWrapper, BpmnMetaModelExtensionConstants.E_ATTR_LABEL) && null != label) {
				flowElementExtentionWrapper.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_LABEL, label);
			}
			
			if (ExtensionHelper.isValidDataExtensionAttribute(flowElementWrapper, BpmnMetaModelExtensionConstants.E_ATTR_VERSION)) {
				flowElementExtentionWrapper.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_VERSION, version);
			}
			
			if (ExtensionHelper.isValidDataExtensionAttribute(flowElementWrapper, BpmnMetaModelExtensionConstants.E_ATTR_CHECK_POINT)) {
				flowElementExtentionWrapper.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_CHECK_POINT, checkpoint);
			}
			
			if (ExtensionHelper.isValidDataExtensionAttribute(flowElementWrapper, BpmnMetaModelExtensionConstants.E_ATTR_TOOL_ID) && null != toolId) {
				flowElementExtentionWrapper.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_TOOL_ID, toolId);
			}
		}
	}
	
	/**
	 * Set Flow and Label node points for a flow element
	 * 
	 * @param flowElementWrapper
	 * @param x
	 * @param y
	 * @param isNodePoint
	 */
	public static void setFlowNodePoint(EObjectWrapper<EClass, EObject> flowElementWrapper, double x, double y, boolean isNodePoint) {
		EObjectWrapper<EClass, EObject> pointWrapper = EObjectWrapper.createInstance(BpmnModelClass.EXTN_POINT);
		
		if (pointWrapper != null) {
			pointWrapper.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_X, x);
			pointWrapper.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_Y, y);
		}
		
		EObjectWrapper<EClass, EObject> flowNodeExtensionValueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(flowElementWrapper);
		String point = (isNodePoint) ? BpmnMetaModelExtensionConstants.E_ATTR_NODE_POINT : BpmnMetaModelExtensionConstants.E_ATTR_LABEL_POINT;
		flowNodeExtensionValueWrapper.setAttribute(point, pointWrapper.getEInstance());
	}
	
	/**
	 * Add loop characteristic details for the flow element
	 * 
	 * @param flowElementWrapper
	 * @param type
	 * @param testBefore
	 * @param loopCondition
	 * @param loopCount
	 * @param isSequential
	 * @param loopCardinality
	 * @param behavior
	 * @param completionCondition
	 */
	public static void setLoopCharacteristics(EObjectWrapper<EClass, EObject> flowElementWrapper, String type, boolean testBefore, String loopCondition, String loopCount, boolean isSequential, String loopCardinality, String behavior, String completionCondition) {
		EObjectWrapper<EClass, EObject> loopCharacteristicsWrapper = null;
		
		if (type != null) {
			if (BpmnModelClass.STANDARD_LOOP_CHARACTERISTICS.getName().equals(type)) {
				loopCharacteristicsWrapper = EObjectWrapper.createInstance(BpmnModelClass.STANDARD_LOOP_CHARACTERISTICS);
				loopCharacteristicsWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_TEST_BEFORE, testBefore);
				loopCharacteristicsWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_LOOP_MAXIMUM, loopCount);
				if(null!=loopCondition && !loopCondition.trim().isEmpty())
				{
					EObjectWrapper<EClass, EObject> loopConditionWrapper = createFormalExpression(loopCondition, "boolean", BPMN_EXPRESSION_LANGUAGE_XPATH);
					loopCharacteristicsWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_LOOP_CONDITION, loopConditionWrapper.getEInstance());
				}
			} else if (BpmnModelClass.MULTI_INSTANCE_LOOP_CHARACTERISTICS.getName().equals(type)) {
				loopCharacteristicsWrapper = EObjectWrapper.createInstance(BpmnModelClass.MULTI_INSTANCE_LOOP_CHARACTERISTICS);
				loopCharacteristicsWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_IS_SEQUENTIAL, isSequential);
				loopCharacteristicsWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_TEST_BEFORE, testBefore);
				
				EEnum enumMultiInstanceBehavior = BpmnModelClass.ENUM_MULTI_INSTANCE_BEHAVIOR;
				EEnumLiteral enumLiteral = enumMultiInstanceBehavior.getEEnumLiteral(behavior);
				loopCharacteristicsWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_BEHAVIOR, enumLiteral);
				if(null!=completionCondition && !completionCondition.trim().isEmpty())
				{
					EObjectWrapper<EClass, EObject> completionConditionWrapper = createFormalExpression(completionCondition, "boolean", BPMN_EXPRESSION_LANGUAGE_XPATH);
					loopCharacteristicsWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_COMPLETION_CONDITION, completionConditionWrapper.getEInstance());
				}
				loopCharacteristicsWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_ITERATOR_XSLT, loopCardinality);
			}
			
			flowElementWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_LOOP_CHARACTERISTICS, loopCharacteristicsWrapper.getEInstance());
		}
	}
	
	/**
	 * Create Formal Expression for creating various xpath expressions
	 * 
	 * @param body
	 * @param evaluationType
	 * @param lanuage
	 * @return
	 */
	public static EObjectWrapper<EClass, EObject> createFormalExpression(String body, String evaluationType, String language){
		EObjectWrapper<EClass, EObject> expressionWrapper = EObjectWrapper.createInstance(BpmnModelClass.FORMAL_EXPRESSION);
		
		expressionWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_BODY, CommonProcessUtil.unescapeXml(body));
		expressionWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_LANGUAGE, language);
		
		EObjectWrapper<EClass, EObject> itemDefinitionWrapper = EObjectWrapper.createInstance(BpmnModelClass.ITEM_DEFINITION);
		itemDefinitionWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_STRUCTURE, evaluationType);
		itemDefinitionWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_IS_COLLECTION, false);;
		expressionWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_EVALUATES_TO_TYPE_REF, itemDefinitionWrapper.getEInstance());
		
		return expressionWrapper;
	}
	
	/**
	 * Add timeout details for the flow element.
	 * 
	 * @param flowElementWrapper
	 * @param expression
	 * @param event
	 * @param unit
	 */
	public static void setTimeout(EObjectWrapper<EClass, EObject> flowElementWrapper, String expression, String event, String unit) {
		EObjectWrapper<EClass, EObject> timerDataWrapper = EObjectWrapper.createInstance(BpmnModelClass.EXTN_TIMER_TASK_DATA);
		timerDataWrapper.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_EXPRESSION, CommonProcessUtil.unescapeXml(expression));
		timerDataWrapper.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_EVENT, event);
		
		EEnum timeUnit = BpmnModelClass.ENUM_TIME_UNITS;
		EEnumLiteral timeUnitEnum = timeUnit.getEEnumLiteral(unit);
		timerDataWrapper.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_UNIT, timeUnitEnum);
		
		EObjectWrapper<EClass, EObject> flowNodeExtensionValueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(flowElementWrapper);
		flowNodeExtensionValueWrapper.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_TIMER_DATA, timerDataWrapper);
	}
	
	/**
	 * Set the XSLT input mapper for the flow node
	 * 
	 * @param flowElementWrapper
	 * @param xslt
	 */
	public static void setFlowNodeInputMapperXslt(EObjectWrapper<EClass, EObject> flowElementWrapper, String xslt) {
		CommonProcessUtil.setIOMapperXslt(flowElementWrapper.getEInstance(), CommonProcessUtil.unescapeXml(xslt), true);
	}
	
	/**
	 * Set the XSLT output mapper for the flow node
	 * 
	 * @param flowElementWrapper
	 * @param xslt
	 */
	public static void setFlowNodeOutputMapperXslt(EObjectWrapper<EClass, EObject> flowElementWrapper, String xslt) {
		CommonProcessUtil.setIOMapperXslt(flowElementWrapper.getEInstance(), CommonProcessUtil.unescapeXml(xslt), false);
	}
	
	/**
	 * Add message starter details for Receive Message task
	 * 
	 * @param flowElementWrapper
	 * @param messageStarterId
	 * @param replyTo
	 * @param consume
	 */
	public static void setMessageStarters(EObjectWrapper<EClass, EObject> flowElementWrapper, String messageStarterId, boolean replyTo, boolean consume) {
		EObjectWrapper<EClass, EObject> addDataExtensionValueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(flowElementWrapper);
		if (addDataExtensionValueWrapper.containsAttribute(BpmnMetaModelExtensionConstants.E_ATTR_MESSAGE_STARTERS)) {

			List<EObject> listAttribute = addDataExtensionValueWrapper.getListAttribute(BpmnMetaModelExtensionConstants.E_ATTR_MESSAGE_STARTERS);
			if (listAttribute != null) {
				EObjectWrapper<EClass, EObject> impl = EObjectWrapper.createInstance(BpmnModelClass.EXTN_MESSAGE_STARTER_DATA);
				impl.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_MESSAGE_STARTER, messageStarterId);
				if (replyTo) {
					impl.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_REPLY_TO, replyTo);
					impl.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_CONSUME, false);
				} else {
					impl.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_REPLY_TO, false);
					impl.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_CONSUME, consume);
				}

				addDataExtensionValueWrapper.addToListAttribute(BpmnMetaModelExtensionConstants.E_ATTR_MESSAGE_STARTERS, impl);
			}
		}
	}
	
	/**
	 * Add key expression for Receive Message task
	 * 
	 * @param flowElementWrapper
	 * @param expressions
	 */
	public static void setKeyExpression(EObjectWrapper<EClass, EObject> flowElementWrapper, String expressions) {
		EObjectWrapper<EClass, EObject> flowNodeExtensionValueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(flowElementWrapper);
		if (flowNodeExtensionValueWrapper.containsAttribute(BpmnMetaModelConstants.E_ATTR_JOB_KEY)) {
			if (null != expressions && !expressions.trim().isEmpty()) {
				flowNodeExtensionValueWrapper.setAttribute(
						BpmnMetaModelConstants.E_ATTR_JOB_KEY,
						CommonProcessUtil.unescapeXml(expressions));
			}
		}
	}
	
	/**
	 * Add Web service details for a Web Service task
	 * 
	 * @param flowElementWrapper
	 * @param service
	 * @param port
	 * @param operation
	 * @param soapAction
	 * @param timeout
	 * @param endPoint
	 * @param bindingType
	 */
	public static void setServiceDetails(EObjectWrapper<EClass, EObject> flowElementWrapper, String service, String port, String operation, String soapAction, long timeout, String endPoint, String bindingType) {
		EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(flowElementWrapper);
		if (valueWrapper != null) {
			if (service != null && !service.isEmpty()) valueWrapper.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_SERVICE, service);
			if (port != null && !port.isEmpty()) valueWrapper.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_PORT, port);
			if (operation != null && !operation.isEmpty()) valueWrapper.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_OPERATION, operation);
			if (soapAction != null && !soapAction.isEmpty()) valueWrapper.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_SOAP_ACTION, soapAction);
			if (timeout > 0) valueWrapper.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_TIMEOUT, timeout);
			
			if (bindingType != null && !bindingType.isEmpty()) {
				EEnum binding = BpmnMetaModelExtension.ENUM_WS_BINDING;
				EEnumLiteral bindingTypeEnum = binding.getEEnumLiteral(bindingType);
				valueWrapper.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_BINDING_TYPE, bindingTypeEnum);

				if (bindingTypeEnum != null && bindingTypeEnum.equals(BpmnModelClass.ENUM_WS_BINDING_HTTP)) {
					valueWrapper.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_END_POINT_URL, endPoint);
				} else {
					valueWrapper.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JNDI_PROVIDER_URL, endPoint);
				}
			}
		}
	}
	
	/**
	 * Add implementation URI's for Business Rule Tasks
	 * 
	 * @param flowElementWrapper
	 * @param uri
	 * @param isDeployed
	 */
	public static void setImplementationURIs(EObjectWrapper<EClass, EObject> flowElementWrapper, String uri, boolean isDeployed) {
		EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(flowElementWrapper);
		if (valueWrapper != null) {
			ArrayList<EObject> uriList = new ArrayList<EObject>(
					valueWrapper
							.getListAttribute(BpmnMetaModelExtensionConstants.E_ATTR_IMPLEMENTATIONS));
			if (uriList != null) {
				EObjectWrapper<EClass, EObject> impl = EObjectWrapper.createInstance(BpmnModelClass.EXTN_VRFIMPL_DATA);
				impl.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_URI, uri);
				impl.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_EXPRESSION, "");
				impl.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_DEPLOYED, isDeployed);

				valueWrapper.addToListAttribute(BpmnMetaModelExtensionConstants.E_ATTR_IMPLEMENTATIONS, impl);
			}
		}
	}
	
	/**
	 * Set the gateway direction
	 * 
	 * @param flowElementWrapper
	 * @param direction
	 */
	public static void setGatewayDirection(EObjectWrapper<EClass, EObject> flowElementWrapper, String direction) {
		EEnumWrapper<EEnum, EEnumLiteral> enWrapper = EEnumWrapper.createInstance(BpmnMetaModel.ENUM_GATEWAY_DIRECTION);
		
		ExpandedName expandeddirection = BpmnMetaModel.ENUM_GATEWAY_DIRECTION_CONVERGING.localName.equals(direction) ? BpmnMetaModel.ENUM_GATEWAY_DIRECTION_CONVERGING : 
									BpmnMetaModel.ENUM_GATEWAY_DIRECTION_DIVERGING.localName.equals(direction) ? BpmnMetaModel.ENUM_GATEWAY_DIRECTION_DIVERGING : 
									BpmnMetaModel.ENUM_GATEWAY_DIRECTION_MIXED.localName.equals(direction) ?
									BpmnMetaModel.ENUM_GATEWAY_DIRECTION_MIXED : BpmnMetaModel.ENUM_GATEWAY_DIRECTION_UNSPECIFIED;
		
		EEnumLiteral directionEnum = enWrapper.getEnumLiteral(expandeddirection);
		flowElementWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_GATEWAY_DIRECTION, directionEnum);
	}
	
	/**
	 * Set exclusive Gateway details
	 * 
	 * @param processObjWrapper
	 * @param flowNodeWrapper
	 * @param defaultSequence
	 * @param transformation
	 * @param sequenceId
	 */
	public static void setExclusiveGateway(EObjectWrapper<EClass, EObject> processObjWrapper, EObjectWrapper<EClass, EObject> flowNodeWrapper, String defaultSequence, String transformation, String sequenceId) {
		if (defaultSequence != null && !defaultSequence.isEmpty()) {
			EObjectWrapper<EClass, EObject> defaultSequenceIdWrapper = CommonProcessUtil.getSequenceElementById(processObjWrapper, defaultSequence);
			if (defaultSequenceIdWrapper != null) {
				flowNodeWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_DEFAULT, defaultSequenceIdWrapper.getEInstance());
			}
		}
		
		EObjectWrapper<EClass, EObject> impl = EObjectWrapper.createInstance(BpmnModelClass.EXTN_GATEWAY_EXPRESSION_DATA);
		impl.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_TRANSFORMATION, transformation);
		impl.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_SEQUENCE_FLOW_ID, sequenceId);
		
		EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(flowNodeWrapper);
		valueWrapper.addToListAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JOIN_EXPRESSIONS, impl.getEInstance());
	}
	
	/**
	 * Set Parallel Gateway details
	 * 
	 * @param flowNodeWrapper
	 * @param joinRuleFunction
	 * @param mergeExpression
	 * @param forkRuleFunction
	 */
	public static void setParallelGateway(EObjectWrapper<EClass, EObject> flowNodeWrapper, String joinRuleFunction, String mergeExpression, String forkRuleFunction) {
		EObjectWrapper<EClass, EObject> flowNodeExtensionValueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(flowNodeWrapper);
		
		if (joinRuleFunction != null && mergeExpression != null) {
			flowNodeExtensionValueWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_JOIN_RULEFUNCTION, joinRuleFunction);
			flowNodeExtensionValueWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_MERGE_EXPRESSION, mergeExpression);
		}
	
		if (forkRuleFunction != null) {
			flowNodeExtensionValueWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_FORK_RULEFUNCTION, forkRuleFunction);
		}
	}
	
	/**
	 * Set Text Annotation value
	 * 
	 * @param flowNodeWrapper
	 * @param annotationValue
	 */
	public static void setTextAnnotationValue(EObjectWrapper<EClass, EObject> flowNodeWrapper, String annotationValue) {
		flowNodeWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_TEXT, annotationValue);
	}
}
