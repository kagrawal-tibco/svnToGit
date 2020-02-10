package com.tibco.cep.bpmn.ui.graph.model.command;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.core.BpmnCoreConstants;
import com.tibco.cep.bpmn.core.utils.BpmnModelUtils;
import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModel;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.ontology.symbols.SymbolEntryImpl;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.graph.model.BpmnGraphUIFactory;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelChangeListener;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelController;
import com.tibco.cep.bpmn.ui.graph.model.factory.events.AbstractConnectorUIFactory;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.designtime.core.model.rule.RuleFunction;
import com.tibco.cep.designtime.core.model.rule.Symbols;
import com.tibco.cep.studio.core.index.model.DecisionTableElement;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tomsawyer.drawing.TSConnector;
import com.tomsawyer.graphicaldrawing.TSEConnector;
import com.tomsawyer.graphicaldrawing.TSENode;

/**
 * @author majha
 *
 */
public class TaskGeneralPropertiesUpdateCommand extends NodeGeneralPropertiesUpdateCommand {
	private static final long serialVersionUID = 4856346201817898536L;

	public TaskGeneralPropertiesUpdateCommand(int type,ModelController controller,EClass modelType, ENamedElement extType,TSENode node, Map<String, Object> updateList) {
		super(type,controller,modelType,extType,node, updateList);
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected void doAction() throws Throwable {
		if (updateList.containsKey(BpmnMetaModelConstants.E_ATTR_LOOP_CHARACTERISTICS)) {
			String value = (String) updateList.remove(BpmnMetaModelConstants.E_ATTR_LOOP_CHARACTERISTICS);
			EObjectWrapper<EClass, EObject> loopCharacteristics = null;
			if (value.equals(BpmnUIConstants.NODE_ATTR_TASK_MODE_MULTIPLE)) {
				loopCharacteristics = EObjectWrapper.createInstance(BpmnModelClass.MULTI_INSTANCE_LOOP_CHARACTERISTICS);
				getNodeOrGraph().setAttribute(BpmnUIConstants.NODE_ATTR_TASK_MODE, BpmnUIConstants.NODE_ATTR_TASK_MODE_MULTIPLE);
			} else if (value.equals(BpmnUIConstants.NODE_ATTR_TASK_MODE_LOOP)) {
				loopCharacteristics = EObjectWrapper.createInstance(BpmnModelClass.STANDARD_LOOP_CHARACTERISTICS);
				getNodeOrGraph().setAttribute(BpmnUIConstants.NODE_ATTR_TASK_MODE, BpmnUIConstants.NODE_ATTR_TASK_MODE_LOOP);
			} else {
				getNodeOrGraph().setAttribute(BpmnUIConstants.NODE_ATTR_TASK_MODE, BpmnUIConstants.NODE_ATTR_TASK_MODE_NONE);
			}

			if (loopCharacteristics != null) {
				updateList.put(BpmnMetaModelConstants.E_ATTR_LOOP_CHARACTERISTICS, loopCharacteristics.getEInstance());
				getModelController().getModelChangeAdapterFactory().adapt(loopCharacteristics, ModelChangeListener.class);
			} else {
				updateList.put(BpmnMetaModelConstants.E_ATTR_LOOP_CHARACTERISTICS, null);
			}
		}
		
		if (updateList.containsKey(BpmnMetaModelConstants.E_ATTR_IS_FOR_COMPENSATION)) {
			boolean comp = (Boolean)updateList.get(BpmnMetaModelConstants.E_ATTR_IS_FOR_COMPENSATION);
			getNodeOrGraph().setAttribute(BpmnUIConstants.NODE_ATTR_TASK_MODE_COMPENSATION, comp);
//			if(comp)
//				getNodeOrGraph().setAttribute(BpmnUIConstants.NODE_ATTR_TASK_MODE, BpmnUIConstants.NODE_ATTR_TASK_MODE_COMPENSATION);
//			else
//				getNodeOrGraph().setAttribute(BpmnUIConstants.NODE_ATTR_TASK_MODE, "");
		}
		
		if (updateList.containsKey(BpmnMetaModelConstants.E_ATTR_LOOP_CONDITION)) {
			String body = (String) updateList.remove(BpmnMetaModelConstants.E_ATTR_LOOP_CONDITION);
			EObjectWrapper<EClass, EObject> formalExpression = getModelController().createFormalExpression(body, "boolean", BpmnCoreConstants.BPMN_EXPRESSION_LANGUAGE_XPATH);
			updateList.put(BpmnMetaModelConstants.E_ATTR_LOOP_CONDITION, formalExpression.getEInstance());
		}
		
		if (updateList.containsKey(BpmnMetaModelConstants.E_ATTR_LOOP_CARDINALITY)) {
			String body = (String) updateList.remove(BpmnMetaModelConstants.E_ATTR_LOOP_CARDINALITY);
			EObjectWrapper<EClass, EObject> formalExpression = getModelController().createFormalExpression(body, "integer", BpmnCoreConstants.BPMN_EXPRESSION_LANGUAGE_XPATH);
			updateList.put(BpmnMetaModelConstants.E_ATTR_LOOP_CARDINALITY, formalExpression.getEInstance());
		}
		
		if (updateList.containsKey(BpmnMetaModelConstants.E_ATTR_COMPLETION_CONDITION)) {
			String body = (String) updateList.remove(BpmnMetaModelConstants.E_ATTR_COMPLETION_CONDITION);
			EObjectWrapper<EClass, EObject> formalExpression = getModelController().createFormalExpression(body, "boolean", BpmnCoreConstants.BPMN_EXPRESSION_LANGUAGE_XPATH);
			updateList.put(BpmnMetaModelConstants.E_ATTR_COMPLETION_CONDITION, formalExpression.getEInstance());
		}
		
		if (updateList.containsKey(BpmnMetaModelConstants.E_ATTR_CONDITION)) {
			String body = (String) updateList.remove(BpmnMetaModelConstants.E_ATTR_CONDITION);
			EObjectWrapper<EClass, EObject> formalExpression = getModelController().createFormalExpression(body, "boolean", BpmnCoreConstants.BPMN_EXPRESSION_LANGUAGE_XPATH);
			updateList.put(BpmnMetaModelConstants.E_ATTR_CONDITION, formalExpression.getEInstance());
		}
		
		if(updateList.containsKey(BpmnMetaModelConstants.E_ATTR_BEHAVIOR)){
			String behaviour = (String) updateList.remove(BpmnMetaModelConstants.E_ATTR_BEHAVIOR);
			EEnum enumMultiInstanceBehavior = BpmnModelClass.ENUM_MULTI_INSTANCE_BEHAVIOR;
			EEnumLiteral enumLiteral = enumMultiInstanceBehavior.getEEnumLiteral(behaviour);
			updateList.put(BpmnMetaModelConstants.E_ATTR_BEHAVIOR, enumLiteral);
		}
		if(updateList.containsKey(BpmnMetaModelConstants.E_ATTR_BOUNDARY_EVENT_REFS)){
			EClass eventDefinitionType = (EClass)updateList.get(BpmnMetaModelConstants.E_ATTR_BOUNDARY_EVENT_REFS);
			String toolId = (String)updateList.remove(BpmnMetaModelConstants.E_ATTR_TOOL_ID);
			if(toolId == null)
				toolId = "";
			if(eventDefinitionType != null){
				EObjectWrapper<EClass, EObject> parent =EObjectWrapper.wrap((EObject) getNodeOrGraph().getUserObject());
				EObjectWrapper<EClass, EObject> eventWrapper = getModelController().addBoundaryEvent(parent, eventDefinitionType, toolId);
				ExpandedName classSpec =  BpmnMetaModel.INSTANCE.getExpandedName(eventDefinitionType);
				AbstractConnectorUIFactory nodeUIFactory =
					BpmnGraphUIFactory.getInstance(null).getConnectorUIFactory(
						getNodeOrGraph(), (String)eventWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME),"",toolId,
						BpmnModelClass.BOUNDARY_EVENT,classSpec );
				nodeUIFactory.removeExistingConnectors(getNodeOrGraph());
				TSEConnector addConnector = nodeUIFactory.addConnector(getNodeOrGraph());
				addConnector.setUserObject(eventWrapper.getEInstance());
				
				List<EObject> events= new ArrayList<EObject>();
				events.add(eventWrapper.getEInstance());
				
				updateList.put(BpmnMetaModelConstants.E_ATTR_BOUNDARY_EVENT_REFS,events);
				updateList.put(BpmnMetaModelConstants.E_ATTR_ATTACHED_TO_REF, elementWrapper.getEInstance());
			} else {
				List connectors = getNodeOrGraph().connectors();
				for (Object object : connectors) {
					 getNodeOrGraph().remove((TSConnector)object);
				}
				List<EObject> events = new ArrayList<EObject>();

				updateList.put(
						BpmnMetaModelConstants.E_ATTR_BOUNDARY_EVENT_REFS,
						events);
			}
		}
		// TODO Auto-generated method stub
		super.doAction();
	}
		

	@Override
	protected Map<String, Object> updateModel(Map<String, Object> model) {
		Map<String, Object> props = new HashMap<String, Object>();
		props.putAll(super.updateModel(model));
		EObject object = (EObject)model.get(BpmnMetaModelConstants.E_ATTR_LOOP_CHARACTERISTICS);
		if(object == null)
			object = (EObject)elementWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_LOOP_CHARACTERISTICS);
		 if (object != null) {
			EObjectWrapper<EClass, EObject> wrapper = EObjectWrapper.wrap(object);
			props.putAll(getModelController().updateEmfModel(wrapper, model));
		}
		 
		@SuppressWarnings("unchecked")
		List<EObject> events = (List<EObject>)model.get(BpmnMetaModelConstants.E_ATTR_BOUNDARY_EVENT_REFS);
		if(events == null || events.size() == 0 )
			 events = elementWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_BOUNDARY_EVENT_REFS);
		if(events != null && events.size() >0){
			EObjectWrapper<EClass, EObject> event = EObjectWrapper.wrap(events.get(0));
			props.putAll(getModelController().updateEmfModel(event, model));
		}
		
		if(model.containsKey(BpmnMetaModelConstants.E_ATTR_DATA_INPUTS) || model.containsKey(BpmnMetaModelConstants.E_ATTR_DATA_OUTPUTS)){
			EObject attribute = elementWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_IO_SPECIFICATION);
			EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(attribute);
			props.putAll(getModelController().updateEmfModel(wrap, model));
		}
		
		if(elementWrapper.getEClassType().equals(BpmnModelClass.SEND_TASK)||
				elementWrapper.getEClassType().equals(BpmnModelClass.SERVICE_TASK)||
				elementWrapper.getEClassType().equals(BpmnModelClass.RECEIVE_TASK)||
				elementWrapper.getEClassType().equals(BpmnModelClass.INFERENCE_TASK)){
			if(model.containsKey(BpmnMetaModelExtensionConstants.E_ATTR_INPUT_MAP_XSLT)){
				String expression = (String)updateList.get(BpmnMetaModelExtensionConstants.E_ATTR_INPUT_MAP_XSLT);
				EObjectWrapper<EClass, EObject> createInputDataAssociation = getModelController()
						.createInputDataAssociation(null, null,
								expression);
				List<EObject> associations = new ArrayList<EObject>();
				associations.add(createInputDataAssociation.getEInstance());
				elementWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_DATA_INPUT_ASSOCIATIONS,
						associations);
			}
			
			if(model.containsKey(BpmnMetaModelExtensionConstants.E_ATTR_OUTPUT_MAP_XSLT)){
				String expression = (String)updateList.get(BpmnMetaModelExtensionConstants.E_ATTR_OUTPUT_MAP_XSLT);
				EObjectWrapper<EClass, EObject> createOutputDataAssociation = getModelController()
						.createOutputDataAssociation(null, null,
								expression);
				List<EObject> associations = new ArrayList<EObject>();
				associations.add(createOutputDataAssociation.getEInstance());
				elementWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_DATA_OUTPUT_ASSOCIATIONS,
						associations);
			}
			
		}
		
		if(updateList.containsKey(BpmnMetaModelExtensionConstants.E_ATTR_OUTPUT_MAP_XSLT)){
			EList<EObject> associations = elementWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_DATA_OUTPUT_ASSOCIATIONS);
			if(associations.size() > 0){
				EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(associations.get(0));
				EObjectWrapper<EClass, EObject> attribute = EObjectWrapper.wrap((EObject)wrap.getAttribute(BpmnMetaModelConstants.E_ATTR_TRANSFORMATION));
				props.putAll(getModelController().updateEmfModel(attribute, model));
			}
			
		}
		
		if(updateList.containsKey(BpmnMetaModelExtensionConstants.E_ATTR_INPUT_MAP_XSLT)){
			EList<EObject> associations = elementWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_DATA_INPUT_ASSOCIATIONS);
			if(associations.size() > 0){
				EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(associations.get(0));
				EObjectWrapper<EClass, EObject> attribute = EObjectWrapper.wrap((EObject)wrap.getAttribute(BpmnMetaModelConstants.E_ATTR_TRANSFORMATION));
				props.putAll(getModelController().updateEmfModel(attribute, model));
			}	
		}
		

		return props;
		
	}
	
	
	protected void processForAttachedResource() {
		if(updateList.containsKey(BpmnMetaModelExtensionConstants.E_ATTR_RULEFUNCTION) ||
				updateList.containsKey(BpmnMetaModelExtensionConstants.E_ATTR_VIRTUALRULEFUNCTION)){
			String path = (String)updateList.get(BpmnMetaModelExtensionConstants.E_ATTR_RULEFUNCTION) ;
			if(path == null)
				path = (String)updateList.get(BpmnMetaModelExtensionConstants.E_ATTR_VIRTUALRULEFUNCTION) ;
			
			if(elementWrapper.getEClassType().equals(BpmnModelClass.RULE_FUNCTION_TASK)||
					elementWrapper.getEClassType().equals(BpmnModelClass.BUSINESS_RULE_TASK)){
				EObjectWrapper<EClass, EObject> addDataExtensionValueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(elementWrapper.getEInstance());
				String value = "";
				if(addDataExtensionValueWrapper.containsAttribute(BpmnMetaModelExtensionConstants.E_ATTR_RULEFUNCTION))
					value = addDataExtensionValueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_RULEFUNCTION);
				else
					value = addDataExtensionValueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_VIRTUALRULEFUNCTION);
				if(value == null || !value.equalsIgnoreCase(path)){
					// check for message event
					EObjectWrapper<EClass, EObject> createIOSpecification = getModelController().createIOSpecification();
					updateList.put(BpmnMetaModelConstants.E_ATTR_IO_SPECIFICATION, createIOSpecification.getEInstance());
					updateList.put(BpmnMetaModelConstants.E_ATTR_DATA_INPUT_ASSOCIATIONS, new ArrayList<EObject>());
					updateList.put(BpmnMetaModelConstants.E_ATTR_DATA_OUTPUT_ASSOCIATIONS, new ArrayList<EObject>());
					EObjectWrapper<EClass, EObject> process = getModelController().getModelRoot();
					String projName = process.getAttribute(BpmnMetaModelConstants.E_ATTR_OWNER_PROJECT);
					
					
					DesignerElement element = IndexUtils.getElement(projName, path);
					if (element instanceof RuleElement) {
						RuleElement rule = (RuleElement) element;
						if (rule.getRule() instanceof RuleFunction) {
							processForRuleFunction(projName,
									(RuleFunction) rule.getRule(), createIOSpecification);
						}
					}
					
					if(elementWrapper.getEClassType().equals(BpmnModelClass.BUSINESS_RULE_TASK) ){
						List<EObject> implementationList = new ArrayList<EObject>();
						if (path != null && !path.isEmpty()) {
							List<DesignerElement> allElements = CommonIndexUtils
									.getAllElements(projName,
											ELEMENT_TYPES.DECISION_TABLE);
							for (DesignerElement designerElement : allElements) {
								if (designerElement instanceof DecisionTableElement) {
									DecisionTableElement table = (DecisionTableElement) designerElement;
									Table implementation = (Table) table
											.getImplementation();
									String implementsURI = implementation
											.getImplements();
									if (implementsURI.equals(path)) {
										String uri = table.getFolder()
												+ table.getName();
										implementationList.add(modelController
												.createVrfImplementation(uri)
												.getEInstance());
									}
								}
							}
						}
						
						updateList.put(BpmnMetaModelExtensionConstants.E_ATTR_IMPLEMENTATIONS, implementationList);
					}
				}else{
					updateList.remove(BpmnMetaModelExtensionConstants.E_ATTR_RULEFUNCTION);
					updateList.remove(BpmnMetaModelExtensionConstants.E_ATTR_VIRTUALRULEFUNCTION);
				}
					
			}
		} 
		
		if(updateList.containsKey(BpmnMetaModelExtensionConstants.E_ATTR_JAVA_FILE_PATH)) {
		} 
		
		if (updateList.containsKey(BpmnMetaModelExtensionConstants.E_ATTR_METHOD_NAME)) {
		} 
		
		if (updateList.containsKey(BpmnMetaModelExtensionConstants.E_ATTR_METHOD_RETURN_TYPE)) {
			processJavaTaskAttachedResource();
		} 
				
		EObjectWrapper<EClass, EObject> modelRoot = getModelController().getModelRoot();
		Collection<EObject> flowNodes = BpmnModelUtils.getFlowNodes(modelRoot, BpmnModelClass.DATA_OBJECT);
		EObjectWrapper<EClass, EObject> dataObjectWrap = null;
		if(!flowNodes.isEmpty()){
			dataObjectWrap = EObjectWrapper.wrap(flowNodes.iterator().next());
		}
		if (updateList.containsKey(BpmnMetaModelExtensionConstants.E_ATTR_OUTPUT_MAP_XSLT)) {
			
			if (elementWrapper.getEClassType().equals(BpmnModelClass.RULE_FUNCTION_TASK)) {
				processOutputMapXSLT(BpmnMetaModelExtensionConstants.E_ATTR_RULEFUNCTION, dataObjectWrap);

			} else if(elementWrapper.getEClassType().equals(BpmnModelClass.JAVA_TASK)) {
				processOutputMapXSLT(BpmnMetaModelExtensionConstants.E_ATTR_JAVA_FILE_PATH, dataObjectWrap);
			}
			
//			if (elementWrapper.getEClassType().equals(BpmnModelClass.RULE_FUNCTION_TASK)) {
//			EObjectWrapper<EClass, EObject> addDataExtensionValueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(elementWrapper.getEInstance());
//			String value = "";
//			if(addDataExtensionValueWrapper.containsAttribute(BpmnMetaModelExtensionConstants.E_ATTR_RULEFUNCTION))
//				value = addDataExtensionValueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_RULEFUNCTION);
//			
//			if(value == null || value.trim().isEmpty()){
//				updateList.remove(BpmnMetaModelExtensionConstants.E_ATTR_INPUT_MAP_XSLT);
//			}else{
//				String expression = (String) updateList
//						.get(BpmnMetaModelExtensionConstants.E_ATTR_INPUT_MAP_XSLT);
//				EObject ioSpec = elementWrapper
//						.getAttribute(BpmnMetaModelConstants.E_ATTR_IO_SPECIFICATION);
//				if(ioSpec == null){
//					EObjectWrapper<EClass, EObject> createIOSpecification = getModelController().createIOSpecification();
//					elementWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_IO_SPECIFICATION, createIOSpecification.getEInstance());
//					ioSpec = createIOSpecification.getEInstance();
//				}
//				
//				EObjectWrapper<EClass, EObject> createIOSpecification = EObjectWrapper
//						.wrap(ioSpec);
//				EList<EObject> listAttribute = createIOSpecification
//						.getListAttribute(BpmnMetaModelConstants.E_ATTR_DATA_INPUTS);
//				EObjectWrapper<EClass, EObject> target = null;
//				if (listAttribute.size() > 0) {
//					target = EObjectWrapper.wrap(listAttribute.get(0));
//				}
//				EObjectWrapper<EClass, EObject> createOutputDataAssociation = getModelController()
//						.createInputDataAssociation(dataObjectWrap, target,
//								expression);
//				List<EObject> associations = new ArrayList<EObject>();
//				associations
//						.add(createOutputDataAssociation.getEInstance());
//				EObjectWrapper<EClass, EObject> transformation = EObjectWrapper.wrap((EObject)createOutputDataAssociation.getAttribute(BpmnMetaModelConstants.E_ATTR_TRANSFORMATION));
//				transformation.setAttribute(
//						BpmnMetaModelConstants.E_ATTR_BODY, expression);
//				updateList
//						.put(BpmnMetaModelConstants.E_ATTR_DATA_INPUT_ASSOCIATIONS,
//								associations);
//			}
//		}
		}

		if (updateList
				.containsKey(BpmnMetaModelExtensionConstants.E_ATTR_INPUT_MAP_XSLT)) {
			
			if (elementWrapper.getEClassType().equals(
					BpmnModelClass.RULE_FUNCTION_TASK)) {
				processInputMapXSLT(BpmnMetaModelExtensionConstants.E_ATTR_RULEFUNCTION, dataObjectWrap);
			} else if (elementWrapper.getEClassType().equals(
					BpmnModelClass.BUSINESS_RULE_TASK)) {
				processInputMapXSLT(BpmnMetaModelExtensionConstants.E_ATTR_VIRTUALRULEFUNCTION, dataObjectWrap);
			} else if(elementWrapper.getEClassType().equals
					(BpmnModelClass.JAVA_TASK)) {
				processInputMapXSLT(BpmnMetaModelExtensionConstants.E_ATTR_JAVA_FILE_PATH, dataObjectWrap);
			}

//			if (elementWrapper.getEClassType().equals(
//					BpmnModelClass.RULE_FUNCTION_TASK)
//					|| elementWrapper.getEClassType().equals(
//							BpmnModelClass.BUSINESS_RULE_TASK)) {
//				EObjectWrapper<EClass, EObject> addDataExtensionValueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(elementWrapper.getEInstance());
//				String value = "";
//				if(addDataExtensionValueWrapper.containsAttribute(BpmnMetaModelExtensionConstants.E_ATTR_RULEFUNCTION))
//					value = addDataExtensionValueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_RULEFUNCTION);
//				else
//					value = addDataExtensionValueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_VIRTUALRULEFUNCTION);
//				
//				if(value == null || value.trim().isEmpty()){
//					updateList.remove(BpmnMetaModelExtensionConstants.E_ATTR_INPUT_MAP_XSLT);
//				}else{
//					String expression = (String) updateList
//							.get(BpmnMetaModelExtensionConstants.E_ATTR_INPUT_MAP_XSLT);
//					EObject ioSpec = elementWrapper
//							.getAttribute(BpmnMetaModelConstants.E_ATTR_IO_SPECIFICATION);
//					if(ioSpec == null){
//						EObjectWrapper<EClass, EObject> createIOSpecification = getModelController().createIOSpecification();
//						elementWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_IO_SPECIFICATION, createIOSpecification.getEInstance());
//						ioSpec = createIOSpecification.getEInstance();
//					}
//					
//					EObjectWrapper<EClass, EObject> createIOSpecification = EObjectWrapper
//							.wrap(ioSpec);
//					EList<EObject> listAttribute = createIOSpecification
//							.getListAttribute(BpmnMetaModelConstants.E_ATTR_DATA_INPUTS);
//					EObjectWrapper<EClass, EObject> target = null;
//					if (listAttribute.size() > 0) {
//						target = EObjectWrapper.wrap(listAttribute.get(0));
//					}
//					EObjectWrapper<EClass, EObject> createOutputDataAssociation = getModelController()
//							.createInputDataAssociation(dataObjectWrap, target,
//									expression);
//					List<EObject> associations = new ArrayList<EObject>();
//					associations
//							.add(createOutputDataAssociation.getEInstance());
//					EObjectWrapper<EClass, EObject> transformation = EObjectWrapper.wrap((EObject)createOutputDataAssociation.getAttribute(BpmnMetaModelConstants.E_ATTR_TRANSFORMATION));
//					transformation.setAttribute(
//							BpmnMetaModelConstants.E_ATTR_BODY, expression);
//					updateList
//							.put(BpmnMetaModelConstants.E_ATTR_DATA_INPUT_ASSOCIATIONS,
//									associations);
//				}
//			} 

		}
		
//		if(updateList.containsKey(BpmnMetaModelConstants.E_ATTR_OPERATION_REF)){
//			String object = (String)updateList.get(BpmnMetaModelConstants.E_ATTR_OPERATION_REF);
//			if(object == null){
//				updateList.put(BpmnMetaModelConstants.E_ATTR_OPERATION_REF, null);
//			}else {
//				EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(elementWrapper);
//				String attribute = (String)updateList.get(BpmnMetaModelExtensionConstants.E_ATTR_WSDL);
//				if(attribute == null)
//					attribute = valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_WSDL);
//				EObjectWrapper<EClass, EObject> operationUsingName = null;
//				if(attribute != null && !attribute.isEmpty()){
//					EObjectWrapper<EClass, EObject> process = getModelController().getModelRoot();
//					String projName = process.getAttribute(BpmnMetaModelConstants.E_ATTR_OWNER_PROJECT);
//					WsdlWrapper wsdl = WsdlWrapperFactory.getWsdl( attribute);
//					if(wsdl != null)
//					{
//						 operationUsingName = BpmnCommonIndexUtils.getOperationUsingName(projName, wsdl.getWsdlName(), object);
//					}
//				}
//				if(operationUsingName == null)
//					updateList.put(BpmnMetaModelConstants.E_ATTR_OPERATION_REF, null);
//				else
//					updateList.put(BpmnMetaModelConstants.E_ATTR_OPERATION_REF, operationUsingName.getEInstance());
//			}
//		}
		
	}
	
	private void processInputMapXSLT(String bpmnMetaModelAttr, EObjectWrapper<EClass, EObject> dataObjectWrap) {
		EObjectWrapper<EClass, EObject> addDataExtensionValueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(elementWrapper.getEInstance());
		String value = "";
		if(addDataExtensionValueWrapper.containsAttribute(bpmnMetaModelAttr))
			value = addDataExtensionValueWrapper.getAttribute(bpmnMetaModelAttr);
		
		if(value == null || value.trim().isEmpty()){
			updateList.remove(BpmnMetaModelExtensionConstants.E_ATTR_INPUT_MAP_XSLT);
		}else{
			String expression = (String) updateList
					.get(BpmnMetaModelExtensionConstants.E_ATTR_INPUT_MAP_XSLT);
			EObject ioSpec = elementWrapper
					.getAttribute(BpmnMetaModelConstants.E_ATTR_IO_SPECIFICATION);
			if(ioSpec == null){
				EObjectWrapper<EClass, EObject> createIOSpecification = getModelController().createIOSpecification();
				elementWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_IO_SPECIFICATION, createIOSpecification.getEInstance());
				ioSpec = createIOSpecification.getEInstance();
			}
			
			EObjectWrapper<EClass, EObject> createIOSpecification = EObjectWrapper
					.wrap(ioSpec);
			EList<EObject> listAttribute = createIOSpecification
					.getListAttribute(BpmnMetaModelConstants.E_ATTR_DATA_INPUTS);
			EObjectWrapper<EClass, EObject> target = null;
			if (listAttribute.size() > 0) {
				target = EObjectWrapper.wrap(listAttribute.get(0));
			}
			EObjectWrapper<EClass, EObject> createOutputDataAssociation = getModelController()
					.createInputDataAssociation(dataObjectWrap, target,
							expression);
			List<EObject> associations = new ArrayList<EObject>();
			associations
					.add(createOutputDataAssociation.getEInstance());
			EObjectWrapper<EClass, EObject> transformation = EObjectWrapper.wrap((EObject)createOutputDataAssociation.getAttribute(BpmnMetaModelConstants.E_ATTR_TRANSFORMATION));
			transformation.setAttribute(
					BpmnMetaModelConstants.E_ATTR_BODY, expression);
			updateList
					.put(BpmnMetaModelConstants.E_ATTR_DATA_INPUT_ASSOCIATIONS,
							associations);
		}
	}
	
	private void processOutputMapXSLT(String bpmnMetaModelAttr, EObjectWrapper<EClass, EObject> dataObjectWrap) {
		EObjectWrapper<EClass, EObject> addDataExtensionValueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(elementWrapper.getEInstance());
		String value = "";
		if(addDataExtensionValueWrapper.containsAttribute(bpmnMetaModelAttr))
			value = addDataExtensionValueWrapper.getAttribute(bpmnMetaModelAttr);
		if(value == null || value.trim().isEmpty()){
			updateList.remove(BpmnMetaModelExtensionConstants.E_ATTR_OUTPUT_MAP_XSLT);
		}else{
			String expression = (String)updateList.get(BpmnMetaModelExtensionConstants.E_ATTR_OUTPUT_MAP_XSLT);
			EObject ioSpec = elementWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_IO_SPECIFICATION);
			if(ioSpec == null){
				EObjectWrapper<EClass, EObject> createIOSpecification = getModelController().createIOSpecification();
				elementWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_IO_SPECIFICATION, createIOSpecification.getEInstance());
				ioSpec = createIOSpecification.getEInstance();
			}
			EObjectWrapper<EClass, EObject> createIOSpecification = EObjectWrapper.wrap(ioSpec);
			EList<EObject> listAttribute = createIOSpecification.getListAttribute(BpmnMetaModelConstants.E_ATTR_DATA_OUTPUTS);
			EObjectWrapper<EClass, EObject> source = null;
			if (listAttribute.size() > 0) {
				source = EObjectWrapper.wrap(listAttribute.get(0));
			}
			EObjectWrapper<EClass, EObject> createOutputDataAssociation = getModelController()
					.createOutputDataAssociation(source,
							dataObjectWrap, expression);
			List<EObject> associations = new ArrayList<EObject>();
			associations.add(createOutputDataAssociation
					.getEInstance());
			EObjectWrapper<EClass, EObject> transformation = EObjectWrapper.wrap((EObject)createOutputDataAssociation.getAttribute(BpmnMetaModelConstants.E_ATTR_TRANSFORMATION));
			transformation.setAttribute(BpmnMetaModelConstants.E_ATTR_BODY, expression);
			updateList.put(BpmnMetaModelConstants.E_ATTR_DATA_OUTPUT_ASSOCIATIONS, associations);
		}
	}
	
	private void processForRuleFunction(String projName, RuleFunction func, EObjectWrapper<EClass, EObject> ioSpec){
		
		String returnType = func.getReturnType();
		if (!(returnType == null || returnType.trim().isEmpty())) {
			boolean isArray = false;
			if (returnType.endsWith("[]")) {
				returnType = returnType.replace("[]", "");
				isArray = true;
			}
			EObjectWrapper<EClass, EObject> itemDefinitionUsingLocation = getModelController()
					.getItemDefinitionForRuleElement(func.getFullPath(), true, isArray);
			if(itemDefinitionUsingLocation != null){
				EObjectWrapper<EClass, EObject> createDataOutPut = getModelController()
						.createDataOutPut("return", itemDefinitionUsingLocation);
				ArrayList<EObject> arrayList = new ArrayList<EObject>();
				arrayList.add(createDataOutPut.getEInstance());
				ioSpec.setAttribute(BpmnMetaModelConstants.E_ATTR_DATA_OUTPUTS,
						arrayList);
			}
		}
		
		Symbols syms = func.getSymbols();
		List<EObject> itemDefList = new ArrayList<EObject>();

		EObjectWrapper<EClass, EObject> itemDef = getModelController()
				.getItemDefinitionForRuleElement(func.getFullPath(), false,
						syms.getSymbolList().size() > 1);
		EObjectWrapper<EClass, EObject> createDataInput = getModelController()
				.createDataInput("arguments", itemDef);
		itemDefList.add(createDataInput.getEInstance());

		ioSpec.setAttribute(BpmnMetaModelConstants.E_ATTR_DATA_INPUTS, itemDefList);
	}
	
	private void processJavaTaskAttachedResource() {
		String fullPath = (String)updateList.get(BpmnMetaModelExtensionConstants.E_ATTR_JAVA_FILE_PATH) ;

		EObjectWrapper<EClass, EObject> addDataExtensionValueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(elementWrapper.getEInstance());
		String value = "";
		if(addDataExtensionValueWrapper.containsAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JAVA_FILE_PATH))
			value = addDataExtensionValueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JAVA_FILE_PATH);
		
		@SuppressWarnings("unchecked")
		List<EObject> argObjs = (List<EObject>)updateList.get(BpmnMetaModelExtensionConstants.E_ATTR_METHOD_ARGUMENTS);
		EObject retObj = (EObject)updateList.get(BpmnMetaModelExtensionConstants.E_ATTR_METHOD_RETURN_TYPE);
		List<SymbolEntryImpl> argList =  new ArrayList<SymbolEntryImpl>(); 
		SymbolEntryImpl retSymbol = null;
		//check if argObjs is not null 
		if(argObjs != null && argObjs.size()>0){
			for (EObject object: argObjs) {
				if(object != null) {
					SymbolEntryImpl symbolEntry = new SymbolEntryImpl(object);
					argList.add(symbolEntry);
				}
			}
		}
		if(retObj != null) {
			retSymbol = new SymbolEntryImpl(retObj);
		}

		if(value == null || !value.equalsIgnoreCase(fullPath)){
			// check for message event
			EObjectWrapper<EClass, EObject> createIOSpecification = getModelController().createIOSpecification();
			updateList.put(BpmnMetaModelConstants.E_ATTR_IO_SPECIFICATION, createIOSpecification.getEInstance());
			updateList.put(BpmnMetaModelConstants.E_ATTR_DATA_INPUT_ASSOCIATIONS, new ArrayList<EObject>());
			updateList.put(BpmnMetaModelConstants.E_ATTR_DATA_OUTPUT_ASSOCIATIONS, new ArrayList<EObject>());
			EObjectWrapper<EClass, EObject> process = getModelController().getModelRoot();
			String projName = process.getAttribute(BpmnMetaModelConstants.E_ATTR_OWNER_PROJECT);
			processForJavaResource(projName, value, argList, retSymbol, createIOSpecification);
		}else{
			updateList.remove(BpmnMetaModelExtensionConstants.E_ATTR_JAVA_FILE_PATH);
		}
	}
	
	
	private void processForJavaResource(String projName, String fullPath, List<SymbolEntryImpl> symbols, SymbolEntryImpl returnTypeSymbol ,EObjectWrapper<EClass, EObject> ioSpec){

		EObjectWrapper<EClass, EObject> typeWrapper = EObjectWrapper.wrap(returnTypeSymbol.getType());
    	String returnType = typeWrapper.toString();
    	boolean isArray = returnTypeSymbol.isArray();
    	if (!(returnType == null || returnType.trim().isEmpty())) {
    		EObjectWrapper<EClass, EObject> itemDefinitionUsingLocation = getModelController()
    				.getItemDefinitionForRuleElement(fullPath, true, isArray);
    		if(itemDefinitionUsingLocation != null){
    			EObjectWrapper<EClass, EObject> createDataOutPut = getModelController()
    					.createDataOutPut("return", itemDefinitionUsingLocation);
    			ArrayList<EObject> arrayList = new ArrayList<EObject>();
    			arrayList.add(createDataOutPut.getEInstance());
    			ioSpec.setAttribute(BpmnMetaModelConstants.E_ATTR_DATA_OUTPUTS,
    					arrayList);
    		}
    	}

		List<EObject> itemDefList = new ArrayList<EObject>();

		EObjectWrapper<EClass, EObject> itemDef = getModelController()
				.getItemDefinitionForRuleElement(fullPath, false,
						symbols.size() > 1);
		EObjectWrapper<EClass, EObject> createDataInput = getModelController()
				.createDataInput("arguments", itemDef);
		itemDefList.add(createDataInput.getEInstance());

		ioSpec.setAttribute(BpmnMetaModelConstants.E_ATTR_DATA_INPUTS, itemDefList);
	}
	
}
