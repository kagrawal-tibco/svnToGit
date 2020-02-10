/**
 * 
 */
package com.tibco.be.ws.process;

import static com.tibco.be.ws.process.ProcessPropertyConstants.PROP_DOC_ID;
import static com.tibco.be.ws.process.ProcessPropertyConstants.PROP_DOC_TEXT;
import static com.tibco.be.ws.process.ProcessPropertyConstants.PROP_EXPRESSION;
import static com.tibco.be.ws.process.ProcessPropertyConstants.PROP_ID;
import static com.tibco.be.ws.process.ProcessPropertyConstants.PROP_ISIMMEDIATE;
import static com.tibco.be.ws.process.ProcessPropertyConstants.PROP_LABEL;
import static com.tibco.be.ws.process.ProcessPropertyConstants.PROP_NAME;
import static com.tibco.be.ws.process.ProcessPropertyConstants.PROP_POINT_X;
import static com.tibco.be.ws.process.ProcessPropertyConstants.PROP_POINT_Y;
import static com.tibco.be.ws.process.ProcessPropertyConstants.PROP_REFERENCEIDS;
import static com.tibco.be.ws.process.ProcessPropertyConstants.PROP_UNIQUEID;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModel;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.BpmnCommonModelUtils;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.model.designtime.utils.Identifier;

/**
 * API's to interact with Process Sequence Flows
 * 
 * @author vpatil
 */
public class SequenceFlowHelper {
	
	/**
	 * Retrieves all the sequence flows
	 * 
	 * @param processObjWrapper
	 * @return
	 */
	public static EObject[] getAllSequenceFlows(EObjectWrapper<EClass, EObject> processObjWrapper) {
		List<EObject> allSequenceFlows = BpmnCommonModelUtils.getAllSequenceFlows(processObjWrapper.getEInstance());
		if (allSequenceFlows != null && allSequenceFlows.size() > 0) {
			return allSequenceFlows.toArray(new EObject[allSequenceFlows.size()]);
		}
		return null;
	}
	
	/**
	 * Retrieves all the associations
	 * 
	 * @param processObjWrapper
	 * @return
	 */
	public static EObject[] getAllAssociations(EObjectWrapper<EClass, EObject> processObjWrapper) {
		Collection<EObject> allAssociations = BpmnCommonModelUtils.getAssociations(processObjWrapper);
		if (allAssociations != null && allAssociations.size() > 0) {
			return allAssociations.toArray(new EObject[allAssociations.size()]);
		}
		return null;
	}
	
	/**
	 * Retrieve Base Sequence Details
	 * 
	 * @param sequenceObject
	 * @return
	 */
	public static Map<String, Object> getSequenceDetails(EObject sequenceObject) {
		Map<String, Object> sequenceDetails = new HashMap<String,Object>();
		
		EObjectWrapper<EClass, EObject> sequenceWrapperObject = EObjectWrapper.wrap(sequenceObject);
		if (sequenceWrapperObject.containsAttribute(BpmnMetaModelConstants.E_ATTR_NAME)) {
			String seqName = sequenceWrapperObject.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
			sequenceDetails.put(PROP_NAME, seqName);
		}
		EObjectWrapper<EClass, EObject> sequenceExtensionValueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(sequenceWrapperObject);
		if (null!=sequenceExtensionValueWrapper && sequenceExtensionValueWrapper.containsAttribute(BpmnMetaModelConstants.E_ATTR_LABEL)) {
			String label = sequenceExtensionValueWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_LABEL);
			sequenceDetails.put(PROP_LABEL, label);
		}
		String locationId = sequenceWrapperObject.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
		sequenceDetails.put(PROP_ID, locationId);
		int unique_id = sequenceWrapperObject.getAttribute(BpmnMetaModelConstants.E_ATTR_UNIQUE_ID);
		sequenceDetails.put(PROP_UNIQUEID, unique_id);
		if (sequenceWrapperObject.containsAttribute(BpmnMetaModelConstants.E_ATTR_IS_IMMEDIATE)) {
			boolean isImmediate = sequenceWrapperObject.getAttribute(BpmnMetaModelConstants.E_ATTR_IS_IMMEDIATE);
			sequenceDetails.put(PROP_ISIMMEDIATE, isImmediate);
		}
		if (sequenceWrapperObject.containsAttribute(BpmnMetaModelConstants.E_ATTR_CONDITION_EXPRESSION)) {
			EObject condExpr = (EObject)sequenceWrapperObject.getAttribute(BpmnMetaModelConstants.E_ATTR_CONDITION_EXPRESSION);
			if (condExpr != null) {
				EObjectWrapper<EClass, EObject> conExprInstance = EObjectWrapper.wrap(condExpr);
				Object object = conExprInstance.getAttribute(BpmnMetaModelConstants.E_ATTR_BODY);
				if (object != null) {
					sequenceDetails.put(PROP_EXPRESSION,object.toString());
					
				}
			}
		}
		
		
		return sequenceDetails;
	}
	
	/**
	 * Retrieve the X and Y positions for the sequence, both for Start and End points.
	 * 
	 * @param sequenceObject
	 * @param isStartPoint
	 * @return
	 */
	public static Map<String, Object> getSequencePoints(EObject sequenceObject, boolean isStartPoint) {
		Map<String, Object> sequencePointDetails = new HashMap<String,Object>();
		
		EObjectWrapper<EClass, EObject> sequenceWrapperObject = EObjectWrapper.wrap(sequenceObject);
		EObjectWrapper<EClass, EObject> sequenceExtensionValueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(sequenceWrapperObject);
		String point = (isStartPoint) ? BpmnMetaModelExtensionConstants.E_ATTR_START_POINT : BpmnMetaModelExtensionConstants.E_ATTR_END_POINT;
		
		EObject pointObject = sequenceExtensionValueWrapper.getAttribute((point));
		if (pointObject != null) {
			EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(pointObject);
			Double x = wrap.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_X);
			sequencePointDetails.put(PROP_POINT_X, x);
			Double y = wrap.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_Y);
			sequencePointDetails.put(PROP_POINT_Y, y);
			
		}
		return sequencePointDetails;
	}
	
	/**
	 * Retrieve the list of bend points associated with the sequence
	 * 
	 * @param sequenceObject
	 * @return
	 */
	public static EObject[] getBendPointList(EObject sequenceObject) {
		EObjectWrapper<EClass, EObject> sequenceWrapperObject = EObjectWrapper.wrap(sequenceObject);
		EObjectWrapper<EClass, EObject> sequenceExtensionValueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(sequenceWrapperObject);
		EList<EObject> bendPointList = sequenceExtensionValueWrapper.getListAttribute((BpmnMetaModelExtensionConstants.E_ATTR_BEND_POINTS));
		
		if (bendPointList != null) {
			return bendPointList.toArray(new EObject[bendPointList.size()]);
		}
		return null;
	}
	
	/**
	 * Retrieve individual bend point details
	 * 
	 * @param bendPoint
	 * @return
	 */
	public static Map<String, Object> getBendPoints(EObject bendPoint) {
		Map<String, Object> bendPointDetails = new HashMap<String,Object>();
		
		if (bendPoint != null) {
			EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(bendPoint);
			Double x = wrap.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_X);
			bendPointDetails.put(PROP_POINT_X, x);
			Double y = wrap.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_Y);
			bendPointDetails.put(PROP_POINT_Y, y);
		}
		
		return bendPointDetails;
	}
	
	/**
	 * Retrieve the source and target reference Id for the Sequence Flows
	 * 
	 * @param sequenceObject
	 * @return
	 */
	public static Map<String, Object> getSequenceReferences(EObject sequenceObject, boolean isSourceReference) {
		Map<String, Object> sequenceReferenceDetails = new HashMap<String,Object>();
		
		EObjectWrapper<EClass, EObject> sequenceWrapperObject = EObjectWrapper.wrap(sequenceObject);
		String reference = (isSourceReference) ? BpmnMetaModelConstants.E_ATTR_SOURCE_REF : BpmnMetaModelConstants.E_ATTR_TARGET_REF;
		EObject referenceObject = sequenceWrapperObject.getAttribute(reference);
		
		String referenceIds = "";
		if (referenceObject != null) {
			EObjectWrapper<EClass, EObject> referenceWrapper = EObjectWrapper.wrap(referenceObject);
			String id = referenceWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
			referenceIds += id + ",";

			// NOTE - Not adding Name for now, Id's should be enough
			if (referenceWrapper.containsAttribute(BpmnMetaModelConstants.E_ATTR_NAME)) {
				String refName = referenceWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
			}
		}
		
		if (!referenceIds.isEmpty()) {
			referenceIds = referenceIds.substring(0, referenceIds.length() - 1);
			sequenceReferenceDetails.put(PROP_REFERENCEIDS, referenceIds);
		}
		
		return sequenceReferenceDetails;
	}
	
	/**
	 * Retrieves Documentation details
	 * 
	 * @param sequenceObject
	 * @return
	 */
	public static Object getDocumentationDetails(EObject sequenceObject) {
		Map<String, Object> documentationDetails = new HashMap<String, Object>();
		
		EObjectWrapper<EClass, EObject> sequenceWrapper = EObjectWrapper.wrap(sequenceObject);
		
		if (sequenceWrapper.containsAttribute(BpmnMetaModelConstants.E_ATTR_DOCUMENTATION)) {
			EList<EObject> documentationList = sequenceWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_DOCUMENTATION);
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
	 * Sets the documentation details
	 * 
	 * @param processObjWrapper
	 * @param sequenceFlowWrapper
	 * @param docId
	 * @param docText
	 * @param name
	 */
	public static void setDocumentation(EObjectWrapper<EClass, EObject> processObjWrapper, EObjectWrapper<EClass, EObject> sequenceFlowWrapper, String docId, String docText, String name) {
		if (docId == null) {
			Identifier flowElementIdentifier = CommonProcessUtil.getNextIdentifier(processObjWrapper.getEInstance(), sequenceFlowWrapper.getEClassType(), processObjWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_OWNER_PROJECT).toString(), name);
			docId = flowElementIdentifier.getId();
		}
		
		EObjectWrapper<EClass, EObject> docWrapper = EObjectWrapper.createInstance(BpmnMetaModel.DOCUMENTATION);
		docWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_ID, docId);
		docWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_TEXT, docText);
		sequenceFlowWrapper.addToListAttribute(BpmnMetaModelConstants.E_ATTR_DOCUMENTATION, docWrapper);
	}
	
	/**
	 * Create a new Sequence Flow EMF model object
	 * 
	 * @param processObjWrapper
	 * @param laneIds
	 * @param srcRef
	 * @param targetRef
	 * @return
	 */
	public static EObjectWrapper<EClass, EObject> createSequenceFlow(EObjectWrapper<EClass, EObject> processObjWrapper, String[] laneIds, String srcRef, String targetRef) {
		EObjectWrapper<EClass, EObject> sequenceFlowWrapper = FlowNodeHelper.createFlowNode(processObjWrapper, BpmnModelClass.SEQUENCE_FLOW.getName(), laneIds);
		
		EObjectWrapper<EClass, EObject> sourceReference = CommonProcessUtil.getFlowElementById(processObjWrapper, srcRef);
		sequenceFlowWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_SOURCE_REF, sourceReference.getEInstance());
		sourceReference.addToListAttribute(BpmnMetaModelConstants.E_ATTR_OUTGOING, sequenceFlowWrapper);
		
		EObjectWrapper<EClass, EObject> targetReference = CommonProcessUtil.getFlowElementById(processObjWrapper, targetRef);
		sequenceFlowWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_TARGET_REF, targetReference.getEInstance());
		targetReference.addToListAttribute(BpmnMetaModelConstants.E_ATTR_INCOMING, sequenceFlowWrapper);
		
		processObjWrapper.addToListAttribute(BpmnMetaModelConstants.E_ATTR_FLOW_ELEMENTS, sequenceFlowWrapper);
		
		return sequenceFlowWrapper;
	}
	
	/**
	 * Set Base attributes for the Sequence Flow
	 * 
	 * @param sequenceFlowWrapper
	 * @param id
	 * @param uniqueId
	 * @param name
	 * @param isImmediate
	 * @param sequenceId
	 * @param expression 
	 */
	public static void setBaseSequenceFlowAttributes(EObjectWrapper<EClass, EObject> processObjWrapper, EObjectWrapper<EClass, EObject> sequenceFlowWrapper, int uniqueId, String name, boolean isImmediate, String sequenceId, String expression) {
		FlowNodeHelper.setFlowElementBaseAttributes(processObjWrapper, sequenceFlowWrapper, uniqueId, name, null, sequenceId);
		if (sequenceFlowWrapper.containsAttribute(BpmnMetaModelConstants.E_ATTR_IS_IMMEDIATE)) {
			sequenceFlowWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_IS_IMMEDIATE, isImmediate);
		}		
		if(null!=expression && !expression.trim().isEmpty()){
			EObjectWrapper<EClass, EObject> createFormalExpression = createFormalExpression(expression, "boolean", FlowNodeHelper.BPMN_EXPRESSION_LANGUAGE_XPATH);
			sequenceFlowWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_CONDITION_EXPRESSION, createFormalExpression.getEInstance());
		}
	}
	
	/**
	 * Set Start and End node points for a flow element
	 * 
	 * @param flowElementWrapper
	 * @param x
	 * @param y
	 * @param isNodePoint
	 */
	public static void setFlowNodePoint(EObjectWrapper<EClass, EObject> sequenceFlowWrapper, double x, double y, boolean isStartPoint) {
		EObjectWrapper<EClass, EObject> pointWrapper = EObjectWrapper.createInstance(BpmnModelClass.EXTN_POINT);
		
		if (pointWrapper != null) {
			pointWrapper.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_X, x);
			pointWrapper.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_Y, y);
		}
		
		EObjectWrapper<EClass, EObject> sequenceFlowExtensionValueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(sequenceFlowWrapper);
		String point = (isStartPoint) ? BpmnMetaModelExtensionConstants.E_ATTR_START_POINT : BpmnMetaModelExtensionConstants.E_ATTR_END_POINT;
		sequenceFlowExtensionValueWrapper.setAttribute(point, pointWrapper.getEInstance());
	}
	
	/**
	 * Set bend points for a sequence element
	 * 
	 * @param sequenceFlowWrapper
	 * @param x
	 * @param y
	 */
	public static void setBendPoint(EObjectWrapper<EClass, EObject> sequenceFlowWrapper, double x, double y) {
		EObjectWrapper<EClass, EObject> sequenceExtentionValueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(sequenceFlowWrapper);
		
		EObjectWrapper<EClass, EObject> pointWrapper = EObjectWrapper.createInstance(BpmnModelClass.EXTN_POINT);

		if (pointWrapper != null) {
			pointWrapper.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_X, x);
			pointWrapper.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_Y, y);
		}

		sequenceExtentionValueWrapper.addToListAttribute(BpmnMetaModelExtensionConstants.E_ATTR_BEND_POINTS, pointWrapper);
	}
	
	/**
	 * Create a new association node
	 * 
	 * @param processObjWrapper
	 * @param srcRef
	 * @param targetRef
	 * @param label
	 * @param id
	 * 
	 * @return
	 */
	public static EObjectWrapper<EClass, EObject> createAssociationNode(EObjectWrapper<EClass, EObject> processObjWrapper, String srcRef, String targetRef, String label, String id) {
		EObjectWrapper<EClass, EObject> associationWrapper = EObjectWrapper.createInstance(BpmnMetaModel.ASSOCIATION);
		
		EObjectWrapper<EClass, EObject> sourceReference = CommonProcessUtil.getFlowElementById(processObjWrapper, srcRef);
		if (sourceReference == null) {
			sourceReference = CommonProcessUtil.getTextAnnotationById(processObjWrapper, srcRef);
		}
		EObjectWrapper<EClass, EObject> targetReference = CommonProcessUtil.getFlowElementById(processObjWrapper, targetRef);
		if (targetReference == null) {
			targetReference = CommonProcessUtil.getTextAnnotationById(processObjWrapper, targetRef);
		}
		
		associationWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_ID, id);
		associationWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_SOURCE_REF, sourceReference.getEInstance());
		associationWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_TARGET_REF, targetReference.getEInstance());
		processObjWrapper.addToListAttribute(BpmnMetaModelConstants.E_ATTR_ARTIFACTS, associationWrapper);
		
		EObjectWrapper<EClass, EObject> addDataExtensionValueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(associationWrapper);
		addDataExtensionValueWrapper.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_LABEL, label);
		
		return associationWrapper;
	}
	/**
	 * Create Formal Expression for creating various xpath expressions
	 * 
	 * @param body
	 * @param evaluationType
	 * @param lanuage
	 * @return
	 */
	private static EObjectWrapper<EClass, EObject> createFormalExpression(String body, String evaluationType, String language){
		EObjectWrapper<EClass, EObject> expressionWrapper = EObjectWrapper.createInstance(BpmnModelClass.FORMAL_EXPRESSION);
		
		expressionWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_BODY, body);
		expressionWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_LANGUAGE, language);
		
		EObjectWrapper<EClass, EObject> itemDefinitionWrapper = EObjectWrapper.createInstance(BpmnModelClass.ITEM_DEFINITION);
		itemDefinitionWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_STRUCTURE, evaluationType);
		itemDefinitionWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_IS_COLLECTION, false);;
		expressionWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_EVALUATES_TO_TYPE_REF, itemDefinitionWrapper);
		
		return expressionWrapper;
	}
	
}
