package com.tibco.cep.bpmn.core.doc;

import static com.tibco.cep.bpmn.core.doc.ProcessDocumentationConstants.DOCUMENTATION_PROCESSES_DIRECTORY;
import static com.tibco.cep.bpmn.core.doc.concept.ConceptDocumentationConstants.DOCUMENTATION_CONCEPTS_DIRECTORY;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.core.doc.DocumentationProcessBean.DocumentationVariable;
import com.tibco.cep.bpmn.core.doc.concept.DocumentationConceptBean;
import com.tibco.cep.bpmn.core.index.BpmnIndexUtils;
import com.tibco.cep.bpmn.core.utils.BpmnModelUtils;
import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.model.designtime.utils.ROEObjectWrapper;
import com.tibco.cep.designtime.core.model.HISTORY_POLICY;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.domain.DomainInstance;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.element.impl.PropertyDefinitionImpl;
import com.tibco.cep.studio.core.doc.DocumentationConstants;
import com.tibco.cep.studio.core.doc.DocumentationDescriptor;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.xml.data.primitive.ExpandedName;

/**
 * Utilities class to provide methods for creating different bean objects from their
 * EObject
 * 
 * @author moshaikh
 * 
 */
public class DocumentationBeanUtils {

	/**
	 * Creates a {@link DocumentDataBean} object for the passed EObject of a
	 * BPMN node and populates all applicable the fields.
	 * 
	 * @param eObject
	 * @param desc
	 * @param processName
	 * @param parentProcessName
	 * @return
	 */
	public static DocumentDataBean createNodeBean(EObject eObject, DocumentationDescriptor desc, EObject process,
			EObject parentProcess) {
		ROEObjectWrapper<EClass, EObject> wObject = ROEObjectWrapper.wrap(eObject);
		String elementName = getElementName(eObject);
		if (elementName == null) {
			return null;
		}
		DocumentationFlowElementBean nodeBean = new DocumentationFlowElementBean();
		String id = getElementId(eObject);
		nodeBean.setEntityId(id);
		nodeBean.setCurrentVersionName(desc.project.getName());
		nodeBean.setEobject(eObject);
		if (parentProcess != null) {
			nodeBean.setParentProcessName(getElementName(parentProcess));
		}
		nodeBean.setProcessName(getElementName(process));
		nodeBean.setName(elementName);
		// parentProcessName != null implies current process is a subprocess.
		nodeBean.setDocFilePath(desc.location + "/" + DOCUMENTATION_PROCESSES_DIRECTORY + "/"
				+ (parentProcess != null ? getElementId(parentProcess) + "/" : "") + getElementId(process)
				+ "/" + id + ".html");
		nodeBean.setDocRootDir(desc.location);

		// Description/Documentation
		String description = getDescription(eObject);
		if (description != null && !"".equals(description.trim())) {
			nodeBean.setDescription(description);
		}
		// Checkpoint
		Boolean checkPoint = getCheckPoint(eObject);
		if (checkPoint != null) {
			nodeBean.setCheckpoint(checkPoint);
		}
		// Priority
		Integer priority = getPriority(eObject);
		if (priority != null) {
			nodeBean.setPriority(priority);
		}
		// Resource
		DocumentHyperLink resource = getResource(eObject);
		if (resource != null) {
			nodeBean.setResource(resource);
		}
		// Destination
		DocumentHyperLink destination = getDestination(eObject);
		if (destination != null) {
			nodeBean.setDestination(destination);
		}
		// inputMap
		try {
			Map<String, String> scopeVariables = BpmnDocHelper.getScopeVariablesMapForInputmapper(parentProcess != null ? parentProcess : process, eObject);
			if (scopeVariables != null && !scopeVariables.isEmpty()) {
				for (Entry<String, String> sv : scopeVariables.entrySet()) {
					DocumentHyperLink link = new DocumentHyperLink(sv.getValue(), null, "classFrame", "");
					link.setTargetId(sv.getValue());
					nodeBean.addInputMapScopeVariable(sv.getKey(), link);
				}
			}
		}
		catch (Exception e) {
			//No inputMap for this node
		}
		try {
			Map<String, String> receivingParams = BpmnDocHelper.getReceivingParamsMapForInputmapper(eObject);
			if (receivingParams != null && !receivingParams.isEmpty()) {
				for (Entry<String, String> rp : receivingParams.entrySet()) {
					DocumentHyperLink link = new DocumentHyperLink(rp.getValue(), null, "classFrame", "");
					link.setTargetId(rp.getValue());
					nodeBean.addInputMapReceivingParam(rp.getKey(), link);
				}
			}
		}
		catch (Exception e) {
			//No inputMap for this node
		}
		// outputMap
		try {
			Map<String, String> scopeVariables = BpmnDocHelper.getScopeVariablesMapForOutputmapper(eObject);
			if (scopeVariables != null && !scopeVariables.isEmpty()) {
				for (Entry<String, String> sv : scopeVariables.entrySet()) {
					DocumentHyperLink link = new DocumentHyperLink(sv.getValue(), null, "classFrame", "");
					link.setTargetId(sv.getValue());
					nodeBean.addOutputMapScopeVariable(sv.getKey(), link);
				}
			}
		}
		catch (Exception e) {
			//No outputMap for this node
		}
		try {
			Map<String, String> receivingParams = BpmnDocHelper.getReceivingParamsMapForOutputmapper(parentProcess != null ? parentProcess : process, eObject);
			if (receivingParams != null && !receivingParams.isEmpty()) {
				for (Entry<String, String> rp : receivingParams.entrySet()) {
					DocumentHyperLink link = new DocumentHyperLink(rp.getValue(), null, "classFrame", "");
					link.setTargetId(rp.getValue());
					nodeBean.addOutputMapReceivingParam(rp.getKey(), link);
				}
			}
		}
		catch (Exception e) {
			//No outputMap for this node
		}
		// rules - for inference
		List<DocumentHyperLink> rules = getRules(eObject);
		if (rules != null) {
			nodeBean.setRules(rules);
		}
		// incoming sequences
		List<DocumentHyperLink> incomingSequences = getIncomingSequences(wObject);
		if (incomingSequences != null) {
			for (DocumentHyperLink link : incomingSequences) {
				nodeBean.addIncomingSequence(link);
			}
		}
		// outgoing sequences
		List<DocumentHyperLink> outgoingSequences = getOutgoingSequences(wObject);
		if (outgoingSequences != null) {
			for (DocumentHyperLink link : outgoingSequences) {
				nodeBean.addOutgoingSequence(link);
			}
		}
		// default sequence
		DocumentHyperLink defaultSequence = getDefaultSequence(eObject);
		if (defaultSequence != null) {
			nodeBean.setDefaultSequence(defaultSequence);
		}
		// Merge Expression
		String mergeExpression = getMergeExpression(eObject);
		if (mergeExpression != null) {
			nodeBean.setMergeExpression(mergeExpression);
		}
		// Join/Fork Rulefunction
		DocumentHyperLink joinRuleFunction = getJoinRulefunction(eObject);
		DocumentHyperLink forkRuleFunction = getForkRulefunction(eObject);
		if (joinRuleFunction != null) {
			nodeBean.setJoinRuleFunction(joinRuleFunction);
		}
		if (forkRuleFunction != null) {
			nodeBean.setForkRuleFunction(forkRuleFunction);
		}
		//keyExpression
		String keyExpression = getKeyExpression(eObject);
		if (keyExpression != null) {
			nodeBean.setKeyExpression(keyExpression);
		}
		//implementation URI
		try {
			List<String> implementationURI = BpmnDocHelper.getImplementationsForBusinessRuletask(eObject);
			if(implementationURI != null) {
				for (String uri : implementationURI) {
					DocumentHyperLink link = new DocumentHyperLink(uri, null, "classFrame", "");
					nodeBean.addImplementationURI(link);
				}
			}
		}
		catch (Exception e) {
			//No implementation URI for this node
		}
		//reply/consume events
		try {
			Map<String, Integer> replyConsumeEvents = BpmnDocHelper.getReplyConsumeEventList(eObject);
			if(replyConsumeEvents != null) {
				if(replyConsumeEvents != null && !replyConsumeEvents.isEmpty()) {
					nodeBean.setReplyConsumeEvents(replyConsumeEvents);
				}
			}
		}
		catch (Exception e) {
			//No reply/consume events for this node
		}
		//Transport properties for webService node.
		try {
			Map<String, String> props = BpmnDocHelper.getTransportPropetiesMapForServiceTask(eObject);
			if (props != null) {
				nodeBean.setTransportProperties(props);
			}
		}
		catch (Exception e) {
			//No transport properties for this node
		}
		
		DocumentationBeansStore.putBean(id, nodeBean);
		return nodeBean;
	}

	/**
	 * Creates a {@link DocumentDataBean} object for the passed EObject of a
	 * BPMN sequence flow.
	 * 
	 * @param eObject
	 * @param desc
	 * @param processName
	 * @param parentProcessName
	 * @return
	 */
	public static DocumentDataBean sequenceFlowBean(EObject eObject,
			DocumentationDescriptor desc, EObject process, EObject parentProcess) {
		String elementName = getElementName(eObject);
		if (elementName == null) {
			return null;
		}
		String id = getElementId(eObject);
		DocumentationFlowElementBean sequenceFlowBean = new DocumentationFlowElementBean();
		sequenceFlowBean.setEntityId(id);
		sequenceFlowBean.setCurrentVersionName(desc.project.getName());
		sequenceFlowBean.setEobject(eObject);
		if (parentProcess != null) {
			sequenceFlowBean.setParentProcessName(getElementName(parentProcess));
		}
		sequenceFlowBean.setProcessName(getElementName(process));
		sequenceFlowBean.setName(elementName);
		sequenceFlowBean.setDocFilePath(desc.location + "/" + DOCUMENTATION_PROCESSES_DIRECTORY + "/"
				+ (parentProcess!= null ? getElementId(parentProcess) + "/" : "") + getElementId(process)
				+ "/" + id + ".html");
		sequenceFlowBean.setDocRootDir(desc.location);

		// Description/Documentation
		String description = getDescription(eObject);
		if (description != null && !"".equals(description.trim())) {
			sequenceFlowBean.setDescription(description);
		}

		// target and source reference
		sequenceFlowBean.setTargetRef(getTargetRef(eObject));
		sequenceFlowBean.setSourceRef(getSourceRef(eObject));

		DocumentationBeansStore.putBean(id, sequenceFlowBean);
		return sequenceFlowBean;
	}
	
	/**
	 * Creates a Process summary bean for the specified process.
	 * @param process
	 * @param desc
	 * @return
	 */
	public static DocumentDataBean createProcessSummaryBean(EObject process, DocumentationDescriptor desc) {
		String processName = getProcessLabel(process);
		if (processName == null) {
			return null;
		}
		String processId = getElementId(process);
		DocumentationProcessBean processBean = new DocumentationProcessBean();
		processBean.setEntityId(processId);
		processBean.setName(processName);
		processBean.setAuthor(getAuthor(process));
		processBean.setCurrentVersionName(desc.project.getName());
		String description = getDescription(process);
		if (description != null && !"".equals(description.trim())) {
			processBean.setDescription(description);
		}
		processBean.setProcessType(getProcessType(process));
		processBean.setRevision(getProcessRevision(process));
		//processBean.setSinceVersion(sinceVersion);
		processBean.setVariables(getProcessVariables(process));
		processBean.setConcepts(getProcessConcepts(process, desc.project.getName()));
		processBean.setDocFilePath(desc.location + "/" + DOCUMENTATION_PROCESSES_DIRECTORY + "/"
				+ processId + "/" + DocumentationConstants.TEMPLATE_FILE_PACKAGE_SUMMARY);
		processBean.setDocRootDir(desc.location);
		
		//Use process full path so it is usable from input/output mapper.
		String path = BpmnIndexUtils.getElementPath(process);
		path = path.replace(CommonIndexUtils.DOT + CommonIndexUtils.PROCESS_EXTENSION, "");
		DocumentationBeansStore.putBean(path, processBean);
		return processBean;
	}
	
	/**
	 * Creates a SubProcess bean for the specifed subProcess.
	 * @param process
	 * @param desc
	 * @param parentProcess
	 * @return
	 */
	public static DocumentDataBean createSubProcessSummaryBean(EObject process, DocumentationDescriptor desc, EObject parentProcess) {
		String processName = getElementName(process);
		if (parentProcess == null || processName == null) {
			return null;
		}
		String processId = getElementId(process);
		DocumentationProcessBean processBean = new DocumentationProcessBean();
		processBean.setEntityId(processId);
		processBean.setName(processName);
		processBean.setCheckpoint(getCheckPoint(process));
		processBean.setParentProcessName(getElementName(parentProcess));
		processBean.setTriggerByEvent(getTriggeredByEvent(process));
		processBean.setCurrentVersionName(desc.project.getName());
		String description = getDescription(process);
		if (description != null && !"".equals(description.trim())) {
			processBean.setDescription(description);
		}
		//processBean.setSinceVersion(sinceVersion);
		processBean.setVariables(getProcessVariables(process));
		processBean.setConcepts(getProcessConcepts(process, desc.project.getName()));
		processBean.setDocFilePath(desc.location + "/" + DOCUMENTATION_PROCESSES_DIRECTORY + "/"
				+ getElementId(parentProcess) + "/" + processId + "/" + DocumentationConstants.TEMPLATE_FILE_PACKAGE_SUMMARY);
		processBean.setDocRootDir(desc.location);
		
		DocumentationBeansStore.putBean(processId, processBean);
		return processBean;
	}
	
	/**
	 * Creates a Concept bean for the specified concept.
	 * @param concept
	 * @return
	 */
	public static DocumentDataBean createConceptBean(DesignerElement concept, DocumentationDescriptor desc) {
		
		EntityElement entityElement = (EntityElement)concept;
		Concept indexedEntity = (Concept)entityElement.getEntity();
		
		DocumentationConceptBean conceptBean = new DocumentationConceptBean();
		String conceptName = indexedEntity.getName();
		String description = indexedEntity.getDescription();
		conceptBean.setName(conceptName);
		conceptBean.setCurrentVersionName(desc.project.getName());
		if (description != null && !"".equals(description.trim())) {
			conceptBean.setDescription(description);
		}
		
		//parent concept
		Concept superConcept = indexedEntity.getSuperConcept();
		if (superConcept != null) {
			DocumentHyperLink superConceptLink = new DocumentHyperLink(superConcept.getName(), null, "classFrame", "");
			superConceptLink.setTargetId(superConcept.getFullPath());
			conceptBean.setParentConcept(superConceptLink);	
		}

		//concept-properties
		List<PropertyDefinition> propertyList = indexedEntity.getProperties();
		for(PropertyDefinition property:propertyList) {
			PropertyDefinitionImpl propertyImpl = (PropertyDefinitionImpl) property;
			String propertyType = null;
			DocumentHyperLink propertyLink = null;
			if(propertyImpl.getType() == PROPERTY_TYPES.CONCEPT || propertyImpl.getType() == PROPERTY_TYPES.CONCEPT_REFERENCE) {
				propertyLink = new DocumentHyperLink(propertyImpl.getConceptTypePath(), null, "classFrame", "");
				propertyLink.setTargetId(propertyImpl.getConceptTypePath());
			}
			
			propertyType =  propertyImpl.getType() != null ?  propertyImpl.getType().toString() : null;
			String policy = HISTORY_POLICY.get(propertyImpl.getHistoryPolicy()) != null ? HISTORY_POLICY.get(propertyImpl.getHistoryPolicy()).toString() : null;
			
			//domains
			List<DocumentHyperLink> domains = null;
			for (DomainInstance instance : property.getDomainInstances()) {
				if (domains == null){
					domains = new ArrayList<DocumentHyperLink>();
				}
				DocumentHyperLink link = new DocumentHyperLink(instance.getResourcePath(), null, "classFrame", "");
				link.setTargetId(instance.getResourcePath());
				domains.add(link);
				//Domain domain = IndexUtils.getDomain(desc.project.getName(), instance.getResourcePath());
			}
			conceptBean.addProperty(propertyImpl.getName(), propertyType,
					propertyImpl.isArray(), policy,
					propertyImpl.getHistorySize(), domains, propertyLink);
		}
		conceptBean.setDocFilePath(desc.location + "/" + DOCUMENTATION_CONCEPTS_DIRECTORY + "/" + conceptName + ".html");
		conceptBean.setDocRootDir(desc.location);
		
		DocumentationBeansStore.putBean(indexedEntity.getFullPath(), conceptBean);
		return conceptBean;
	}

	/**
	 * Returns a list of {@link DocumentHyperLink} objects for all incoming
	 * Sequence flows for the specified node.
	 * 
	 * @param wObject
	 * @return
	 */
	private static List<DocumentHyperLink> getIncomingSequences(ROEObjectWrapper<EClass, EObject> wObject) {
		try {
			Collection<EObject> inSequences = (Collection<EObject>) wObject
					.getListAttribute(BpmnMetaModelConstants.E_ATTR_INCOMING);
			List<DocumentHyperLink> sequenceLinks = new ArrayList<DocumentHyperLink>();
			for (EObject sequence : inSequences) {
				DocumentHyperLink link = new DocumentHyperLink(getElementName(sequence), null, "classFrame", "");
				link.setTargetId(getElementId(sequence));
				sequenceLinks.add(link);
			}
			return sequenceLinks;
		}
		catch (Exception e) {
			return null;//No such attribute
		}
	}

	/**
	 * Returns a list of {@link DocumentHyperLink} objects for all outgoing
	 * Sequence flows of the specified node.
	 * 
	 * @param wObject
	 * @return
	 */
	private static List<DocumentHyperLink> getOutgoingSequences(ROEObjectWrapper<EClass, EObject> wObject) {
		try {
			Collection<EObject> outSequences = (Collection<EObject>) wObject
					.getListAttribute(BpmnMetaModelConstants.E_ATTR_OUTGOING);
			List<DocumentHyperLink> sequenceLinks = new ArrayList<DocumentHyperLink>();
			for (EObject sequence : outSequences) {
				DocumentHyperLink link = new DocumentHyperLink(getElementName(sequence), null, "classFrame", "");
				link.setTargetId(getElementId(sequence));
				sequenceLinks.add(link);
			}
			return sequenceLinks;
		}
		catch (Exception e) {
			return null;//No such attribute
		}
	}

	/**
	 * Returns the 'toolId' of the specified BPMN entity.
	 * 
	 * @param wExtension
	 * @return
	 */
	@SuppressWarnings("unused")
	private static String getElementToolId(ROEObjectWrapper<EClass, EObject> wExtension) {
		try {
			String toolId = (String) wExtension.getAttribute(BpmnMetaModelConstants.E_ATTR_TOOL_ID);
			return toolId;
		}
		catch (Exception e) {
			return null;//No such attribute
		}
	}

	/**
	 * Returns 'mergeExpression' of the specified BPMN entity.
	 * 
	 * @param eObject
	 * @return
	 */
	private static String getMergeExpression(EObject eObject) {
		try {
			EObject extension = ExtensionHelper.getAddDataExtensionValue(eObject);
			ROEObjectWrapper<EClass, EObject> wExtension = ROEObjectWrapper.wrap(extension);
			String mergeExpression = (String) wExtension.getAttribute(BpmnMetaModelConstants.E_ATTR_MERGE_EXPRESSION);
			if (mergeExpression == null || "".equals(mergeExpression.trim())) {
				return null;
			}
			return mergeExpression;
		}
		catch (Exception e) {
			return null;//No such attribute
		}
	}

	/**
	 * Returns a {@link DocumentHyperLink} object pointing to 'joinRulefunction'
	 * of the specified BPMN entity.
	 * 
	 * @param eObject
	 * @return
	 */
	private static DocumentHyperLink getJoinRulefunction(EObject eObject) {
		try {
			EObject extension = ExtensionHelper.getAddDataExtensionValue(eObject);
			ROEObjectWrapper<EClass, EObject> wExtension = ROEObjectWrapper.wrap(extension);
			String joinRulefunction = (String) wExtension.getAttribute(BpmnMetaModelConstants.E_ATTR_JOIN_RULEFUNCTION);
			DocumentHyperLink link = new DocumentHyperLink(joinRulefunction, null,
					"classFrame", "");
			// TODO: create link
			return link;
		}
		catch (Exception e) {
			return null;//No such attribute
		}
	}

	/**
	 * Returns a {@link DocumentHyperLink} object pointing to 'forkRulefunction'
	 * of the specified BPMN entity.
	 * 
	 * @param eObject
	 * @return
	 */
	private static DocumentHyperLink getForkRulefunction(EObject eObject) {
		try {
			EObject extension = ExtensionHelper.getAddDataExtensionValue(eObject);
			ROEObjectWrapper<EClass, EObject> wExtension = ROEObjectWrapper.wrap(extension);
			String formRulefunction = (String) wExtension
					.getAttribute(BpmnMetaModelConstants.E_ATTR_FORK_RULEFUNCTION);
			DocumentHyperLink link = new DocumentHyperLink(formRulefunction, null, "classFrame", "");
			// TODO: create link
			return link;
		}
		catch (Exception e) {
			return null;//No such attribute
		}
	}

	/**
	 * Returns a {@link DocumentHyperLink} object pointing to 'default' sequence
	 * of specified Exclusive gateway.
	 * 
	 * @param wObject
	 * @return
	 */
	private static DocumentHyperLink getDefaultSequence(EObject eObject) {
		try {
			ROEObjectWrapper<EClass, EObject> wObject = ROEObjectWrapper.wrap(eObject);
			EObject defaultSeq = (EObject) wObject.getAttribute(BpmnMetaModelConstants.E_ATTR_DEFAULT);
			if (defaultSeq == null) {
				return null;
			}
			DocumentHyperLink defaultSequenceLink = new DocumentHyperLink(getElementName(defaultSeq), null, "classFrame", "");
			defaultSequenceLink.setTargetId(getElementId(defaultSeq));
			return defaultSequenceLink;
		}
		catch (Exception e) {
			return null;//No such attribute
		}
	}

	/**
	 * Returns a list of {@link DocumentHyperLink} objects for the specified
	 * Inference node.
	 * 
	 * @param eObject
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static List<DocumentHyperLink> getRules(EObject eObject) {
		try {
			EObject extension = ExtensionHelper.getAddDataExtensionValue(eObject);
			ROEObjectWrapper<EClass, EObject> wExtension = ROEObjectWrapper.wrap(extension);
			Collection<String> rules = (Collection<String>) wExtension
					.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_RULES);
			List<DocumentHyperLink> ruleLinks = new ArrayList<DocumentHyperLink>();
			for (String rule : rules) {
				// TODO create link to the rule page.
				ruleLinks.add(new DocumentHyperLink(rule, null, "classFrame", ""));
			}
			return ruleLinks;
		}
		catch (Exception e) {
			return null;//No such attribute
		}
	}

	/**
	 * Returns a {@link DocumentHyperLink} object pointing to the 'tergetRef' of
	 * the specified sequence flow.
	 * 
	 * @param eObject
	 * @return
	 */
	private static DocumentHyperLink getTargetRef(EObject eObject) {
		try {
			ROEObjectWrapper<EClass, EObject> wObject = ROEObjectWrapper.wrap(eObject);
			EObject targetRef = (EObject) wObject.getAttribute(BpmnMetaModelConstants.E_ATTR_TARGET_REF);
			DocumentHyperLink targetLink = new DocumentHyperLink(
					getElementName(targetRef), null, "classFrame", "");
			targetLink.setTargetId(getElementId(targetRef));
			return targetLink;
		}
		catch (Exception e) {
			return null;//No such attribute
		}
	}

	/**
	 * Returns a {@link DocumentHyperLink} object pointing to the specified
	 * 'sourceRef' of the specified sequence flow.
	 * 
	 * @param eObject
	 * @return
	 */
	private static DocumentHyperLink getSourceRef(EObject eObject) {
		try {
			ROEObjectWrapper<EClass, EObject> wObject = ROEObjectWrapper.wrap(eObject);
			EObject sourceRef = (EObject) wObject.getAttribute(BpmnMetaModelConstants.E_ATTR_SOURCE_REF);
			DocumentHyperLink sourceLink = new DocumentHyperLink(getElementName(sourceRef), null, "classFrame", "");
			sourceLink.setTargetId(getElementId(sourceRef));
			return sourceLink;
		}
		catch (Exception e) {
			return null;//No such attribute
		}
	}

	/**
	 * Returns the 'name' of the specified BPMN entity.
	 * 
	 * @param eObject
	 * @return
	 */
	private static String getElementName(EObject eObject) {
		ROEObjectWrapper<EClass, EObject> wObject = ROEObjectWrapper.wrap(eObject);
		String elementName = (String) wObject.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
		if (elementName == null || "".equals(elementName.trim())) {
			String id = (String) wObject.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
			if (id == null || "".equals(id.trim())) {
				return null;
			} else {
				elementName = id.lastIndexOf('.') != -1 ? id.substring(id.lastIndexOf('.') + 1) : id;
			}
		}
		return elementName;
	}
	
	/**
	 * Returns the 'label' of the specified process, returns 'name' if label is blank.
	 * 
	 * @param process
	 * @return
	 */
	public static String getProcessLabel(EObject process) {
		try {
			EObject extension = ExtensionHelper.getAddDataExtensionValue(process);
			ROEObjectWrapper<EClass, EObject> wExtension = ROEObjectWrapper.wrap(extension);
			String label = (String) wExtension.getAttribute(BpmnMetaModelConstants.E_ATTR_LABEL);
			if (label == null || "".equals(label.trim())) {
				return getElementName(process);
			}
			return label;
		} catch (Exception e) {
			return null;// No such attribute
		}
	}

	/**
	 * Returns the 'id' of the specified BPMN entity.
	 * 
	 * @param eObject
	 * @return
	 */
	public static String getElementId(EObject eObject) {
		try {
			ROEObjectWrapper<EClass, EObject> wObject = ROEObjectWrapper.wrap(eObject);
			String id = (String) wObject.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
			return id;
		}
		catch (Exception e) {
			return null;//No such attribute
		}
	}

	/**
	 * Returns a {@link DocumentHyperLink} object pointing to the 'resource' of
	 * the specified BPMN node.
	 * 
	 * @param element
	 * @return
	 */
	private static DocumentHyperLink getResource(EObject element) {
		try {
			String resourceString = null;
			Object obj = BpmnModelUtils.getAttachedResource(element);
			if (obj == null) {
				return null;
			}
			if (obj instanceof String) {
				resourceString = (String) BpmnModelUtils.getAttachedResource(element);
			} else if (element.eClass().equals(BpmnModelClass.CALL_ACTIVITY)) {
				EObject callAtivity = (EObject) BpmnModelUtils.getAttachedResource(element);
				EObjectWrapper<EClass, EObject> callActivityWrapper = EObjectWrapper.wrap(callAtivity);
				resourceString = callActivityWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
			}
	
			DocumentHyperLink resource = null;
			if (resourceString != null) {
				// TODO: create link
				resource = new DocumentHyperLink(resourceString, null,"classFrame", "");
			}
			return resource;
		}
		catch (Exception e) {
			return null;// No such attribute
		}
	}

	/**
	 * Returns a {@link DocumentHyperLink} object pointing to 'destination' of
	 * the specified BPMN node.
	 * 
	 * @param eObject
	 * @return
	 */
	private static DocumentHyperLink getDestination(EObject eObject) {
		try {
			EObject extension = ExtensionHelper.getAddDataExtensionValue(eObject);
			ROEObjectWrapper<EClass, EObject> wExtension = ROEObjectWrapper.wrap(extension);
			String destinationString = (String) wExtension.getAttribute(BpmnMetaModelConstants.E_ATTR_DESTINATION);
			DocumentHyperLink destination = null;
			if (destinationString != null && !"".equals(destinationString.trim())) {
				destination = new DocumentHyperLink(destinationString, null, "classFrame", "");
			}
			return destination;
		} catch (Exception e) {
			return null;// No such attribute
		}
	}

	/**
	 * Returns 'documentation' of the specified BPMN entity.
	 * 
	 * @param wObject
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static String getDescription(EObject eObject) {
		try {
			ROEObjectWrapper<EClass, EObject> wObject = ROEObjectWrapper.wrap(eObject);
			EList<EObject> docList = (EList<EObject>) wObject.getAttribute(BpmnMetaModelConstants.E_ATTR_DOCUMENTATION);
			StringBuilder document = new StringBuilder();
			for (EObject docObject : docList) {
				ROEObjectWrapper<EClass, EObject> docWrapper = ROEObjectWrapper.wrap(docObject);
				document.append((String) docWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_TEXT));
			}
			return document.toString();
		}
		catch (Exception e) {
			return null;// No such attribute
		}
	}

	/**
	 * Returns value of 'checkpoint' for the specified BPMN node.
	 * 
	 * @param eObject
	 * @return
	 */
	private static Boolean getCheckPoint(EObject eObject) {
		try {
			EObject extension = ExtensionHelper.getAddDataExtensionValue(eObject);
			ROEObjectWrapper<EClass, EObject> wExtension = ROEObjectWrapper.wrap(extension);
			Boolean checkPoint = (Boolean) wExtension.getAttribute(BpmnMetaModelConstants.E_ATTR_CHECK_POINT);
			return checkPoint;
		} catch (Exception e) {
			return null;// No such attribute
		}
	}

	/**
	 * Returns value of 'priority' of the specified BPMN node.
	 * 
	 * @param eObject
	 * @return
	 */
	private static Integer getPriority(EObject eObject) {
		try {
			EObject extension = ExtensionHelper.getAddDataExtensionValue(eObject);
			ROEObjectWrapper<EClass, EObject> wExtension = ROEObjectWrapper.wrap(extension);
			Integer priority = (Integer) wExtension.getAttribute(BpmnMetaModelConstants.E_ATTR_PRIORITY);
			return priority;
		} catch (Exception e) {
			return null;// No such attribute
		}
	}

	/**
	 * Returns the keyExpression ('jobKey') of the specified Receive Message Node.
	 * @param eObject
	 * @return
	 */
	private static String getKeyExpression(EObject eObject) {
		try {
			EObject extension = ExtensionHelper.getAddDataExtensionValue(eObject);
			ROEObjectWrapper<EClass, EObject> wExtension = ROEObjectWrapper.wrap(extension);
			String keyExpression = (String) wExtension.getAttribute(BpmnMetaModelConstants.E_ATTR_JOB_KEY);
			return keyExpression;
		} catch (Exception e) {
			return null;// No such attribute
		}
	}
	
	/**
	 * Returns the 'author' of the specified process.
	 * @param eObject
	 * @return
	 */
	private static String getAuthor(EObject eObject) {
		try {
			EObject extension = ExtensionHelper.getAddDataExtensionValue(eObject);
			ROEObjectWrapper<EClass, EObject> wExtension = ROEObjectWrapper.wrap(extension);
			String author = wExtension.getAttribute(BpmnMetaModelConstants.E_ATTR_AUTHOR);
			return author;
		} catch (Exception e) {
			return null;// No such attribute
		}
	}
	
	/**
	 * Returns processType of a process.
	 * @param process
	 * @return
	 */
	private static String getProcessType(EObject process) {
		try {
			ROEObjectWrapper<EClass, EObject> processWrapper = ROEObjectWrapper.wrap(process);
			Object processType = processWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_PROCESS_TYPE);
			if(processType != null) {
				EEnumLiteral type =(EEnumLiteral)processType;
				return type.equals(BpmnModelClass.ENUM_PROCESS_TYPE_PUBLIC) ? "Public" : "Private";
			}
		} catch (Exception e) {
			// No such attribute
		}
		return null;
	}
	
	/**
	 * Returns the revision ('version') for a process.
	 * @param process
	 * @return
	 */
	private static Integer getProcessRevision(EObject process) {
		try {
			EObject extension = ExtensionHelper.getAddDataExtensionValue(process);
			ROEObjectWrapper<EClass, EObject> wExtension = ROEObjectWrapper.wrap(extension);
			return (Integer)wExtension.getAttribute(BpmnMetaModelConstants.E_ATTR_VERSION);
		} catch (Exception e) {
			return null;// No such attribute
		}
	}
	
	/**
	 * Returns a list of variables of the specified process.
	 * @param process
	 * @return
	 */
	private static List<DocumentationVariable> getProcessVariables(EObject process) {
		try {
			ROEObjectWrapper<EClass, EObject> processWrapper = ROEObjectWrapper.wrap(process);
			List<DocumentationVariable> variables = new ArrayList<DocumentationProcessBean.DocumentationVariable>();
			
			List<EObject> listAttribute = processWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_PROPERTIES);
			for (EObject eObject : listAttribute) {
				EObjectWrapper<EClass, EObject> propDef = EObjectWrapper
						.wrap(eObject);
				String name = propDef.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
				EObjectWrapper<EClass, EObject> addDataExtensionValueWrapper = ExtensionHelper
						.getAddDataExtensionValueWrapper(propDef);
				EObjectWrapper<EClass, EObject> itemDef = EObjectWrapper.wrap((EObject)propDef.getAttribute(BpmnMetaModelConstants.E_ATTR_ITEM_SUBJECT_REF));
				Boolean multiple =  itemDef.getAttribute(BpmnMetaModelConstants.E_ATTR_IS_COLLECTION);
				EEnumLiteral propType = addDataExtensionValueWrapper
						.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_PROP_TYPE);
				
				if (propType.equals(BpmnModelClass.ENUM_PROPERTY_TYPES_Concept)
						|| propType.equals(BpmnModelClass.ENUM_PROPERTY_TYPES_ConceptReference)) {
					String conceptPath = getPropertyFullPath(itemDef);
					DocumentationVariable variable = new DocumentationVariable(name, conceptPath, multiple);
					DocumentHyperLink link = new DocumentHyperLink(null, null, "classFrame", "");
					link.setTargetId(conceptPath);
					variable.setHyperlink(link);
					variables.add(variable);
				}
				else {
					variables.add(new DocumentationVariable(name, propType.getName(), multiple));
				}
			}
			return variables;
		}
		catch (Exception e) {
			return null;//No variables
		}
	}
	
	/**
	 * Returns a map containing link to concepts and their respective descriptions.
	 * @param process
	 * @param projectName
	 * @return
	 */
	private static Map<DocumentHyperLink,String> getProcessConcepts(EObject process, String projectName) {
		try {
			ROEObjectWrapper<EClass, EObject> processWrapper = ROEObjectWrapper.wrap(process);
			Map<DocumentHyperLink, String> conceptDetails = new LinkedHashMap<DocumentHyperLink, String>();
			
			List<EObject> listAttribute = processWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_PROPERTIES);
			for (EObject eObject : listAttribute) {
				EObjectWrapper<EClass, EObject> propDef = EObjectWrapper
						.wrap(eObject);
				EObjectWrapper<EClass, EObject> addDataExtensionValueWrapper = ExtensionHelper
						.getAddDataExtensionValueWrapper(propDef);
				EObjectWrapper<EClass, EObject> itemDef = EObjectWrapper.wrap((EObject)propDef.getAttribute(BpmnMetaModelConstants.E_ATTR_ITEM_SUBJECT_REF));
				EEnumLiteral propType = addDataExtensionValueWrapper
						.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_PROP_TYPE);
				
				if (propType.equals(BpmnModelClass.ENUM_PROPERTY_TYPES_Concept)
						|| propType.equals(BpmnModelClass.ENUM_PROPERTY_TYPES_ConceptReference)) {
					String conceptPath = getPropertyFullPath(itemDef);
					// TODO: check if concept is available in beansStore, can use description from there
					Concept concept = CommonIndexUtils.getConcept(projectName, conceptPath);
					DocumentHyperLink conceptLink = new DocumentHyperLink(concept.getName(), null, "classFrame", "");
					conceptLink.setTargetId(conceptPath);
					conceptDetails.put(conceptLink, concept.getDescription());
				}
			}
			return conceptDetails;
		}
		catch (Exception e) {
			return null;//No concepts
		}
	}
	
	/**
	 * Returns the 'triggeredByEvent' of the specified sub-process.
	 * 
	 * @param subProcess
	 * @return
	 */
	public static Boolean getTriggeredByEvent(EObject subProcess) {
		try {
			ROEObjectWrapper<EClass, EObject> wSubProcess = ROEObjectWrapper.wrap(subProcess);
			Boolean triggeredByEvent = (Boolean) wSubProcess.getAttribute(BpmnMetaModelConstants.E_ATTR_TRIGGERED_BY_EVENT);
			return triggeredByEvent;
		} catch (Exception e) {
			return null;// No such attribute
		}
	}

	private static String getPropertyFullPath(ROEObjectWrapper<EClass, EObject> itemDef){
		String id = itemDef.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
		if(id!= null){
			ExpandedName parse = ExpandedName.parse(id);
			String localName = parse.localName;
			PROPERTY_TYPES type = PROPERTY_TYPES.get(localName);
			EObject attr = itemDef.getAttribute(BpmnMetaModelConstants.E_ATTR_IMPORT);
			
			if(type == null && attr != null){
				EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(attr);
				String attribute = wrap.getAttribute(BpmnMetaModelConstants.E_ATTR_LOCATION);
				return attribute;
			}
		}
		return null;
	}
}
