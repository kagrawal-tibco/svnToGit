package com.tibco.cep.bpmn.ui.graph.model.command;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.core.BpmnCoreConstants;
import com.tibco.cep.bpmn.core.utils.BpmnModelUtils;
import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelChangeListener;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelController;
import com.tomsawyer.graphicaldrawing.TSENode;

/**
 * @author majha
 *
 */
public class EventGeneralPropertiesUpdateCommand extends NodeGeneralPropertiesUpdateCommand {

	private static final long serialVersionUID = -439168385571854781L;

	public EventGeneralPropertiesUpdateCommand(int type,ModelController controller,EClass modelType, ENamedElement extType,TSENode node, Map<String, Object> updateList) {
		super(type,controller,modelType,extType,node, updateList);
	}	
	
	@Override
	protected void doAction() throws Throwable {
		// TODO Auto-generated method stub
		super.doAction();
		if (elementWrapper != null)
			getModelController().getModelChangeAdapterFactory().adapt(
					elementWrapper, ModelChangeListener.class);
	}
	
	@Override
	protected void undoAction() throws Throwable {
		super.undoAction();
		if(elementWrapper != null)
			getModelController().getModelChangeAdapterFactory().adapt(elementWrapper, ModelChangeListener.class);
	}
	
	@Override
	protected void redoAction() throws Throwable {
		doAction();
	}
	
	protected Map<String, Object> updateModel(Map<String, Object> model) {
		Map<String, Object> props = super.updateModel(model);
		
		if(updateList.containsKey(BpmnMetaModelExtensionConstants.E_ATTR_EVENT) ){
			
			if(elementWrapper.getEClassType().equals(BpmnModelClass.START_EVENT) ||
					elementWrapper.getEClassType().equals(BpmnModelClass.END_EVENT)){
				// check for message event
				EList<EObject> listAttribute = elementWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_EVENT_DEFINITIONS);
				if(listAttribute.size() > 0){
					EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(listAttribute.get(0));
					if(wrap.getEClassType().equals(BpmnModelClass.MESSAGE_EVENT_DEFINITION)){
						props.putAll(getModelController().updateEmfModel(wrap, model));
						EObject message = wrap.getAttribute(BpmnMetaModelConstants.E_ATTR_MESSAGE_REF);
						if(message != null){
							EObjectWrapper<EClass, EObject> msgWrapper = EObjectWrapper.wrap(message);
							props.putAll(getModelController().updateEmfModel(msgWrapper, model));
						}
					}
				}
			}
			
//			if(BpmnModelClass.CATCH_EVENT.isSuperTypeOf(elementWrapper.getEClassType())){
//				EObject dataOutPut = elementWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_DATA_OUTPUT);
//				if(dataOutPut != null){
//					EObjectWrapper<EClass, EObject> dataWrap = EObjectWrapper.wrap(dataOutPut);
//					props.putAll(getModelController().updateEmfModel(dataWrap, model));
//				}
//			}
//			
//			if(BpmnModelClass.THROW_EVENT.isSuperTypeOf(elementWrapper.getEClassType())){
//				EObject dataInput = elementWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_DATA_INPUT);
//				if(dataInput != null){
//					EObjectWrapper<EClass, EObject> dataWrap = EObjectWrapper.wrap(dataInput);
//					props.putAll(getModelController().updateEmfModel(dataWrap, model));
//				}
//			}
		}
//		if(updateList.containsKey(BpmnMetaModelExtensionConstants.E_ATTR_INPUT_MAP_XSLT)){
//			EObjectWrapper<EClass, EObject> association = EObjectWrapper.wrap(elementWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_DATA_INPUT_ASSOCIATION).get(0));
//			EObjectWrapper<EClass, EObject> attribute = EObjectWrapper.wrap((EObject)association.getAttribute(BpmnMetaModelConstants.E_ATTR_TRANSFORMATION));
//			props.putAll(getModelController().updateEmfModel(attribute, model));
//		}
//		
//		if(updateList.containsKey(BpmnMetaModelExtensionConstants.E_ATTR_OUTPUT_MAP_XSLT)){
//			EObjectWrapper<EClass, EObject> association = EObjectWrapper.wrap(elementWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_DATA_OUTPUT_ASSOCIATION).get(0));
//			EObjectWrapper<EClass, EObject> attribute = EObjectWrapper.wrap((EObject)association.getAttribute(BpmnMetaModelConstants.E_ATTR_TRANSFORMATION));
//			props.putAll(getModelController().updateEmfModel(attribute, model));
//		}
		
		if (model.containsKey(BpmnMetaModelConstants.E_ATTR_TIME_CYCLE) ) {
			EList<EObject> listAttribute = elementWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_EVENT_DEFINITIONS);
			if(listAttribute.size() > 0){
				EObject eObject = listAttribute.get(0);
				if(BpmnModelClass.TIMER_EVENT_DEFINITION.isSuperTypeOf(eObject.eClass())){
					Object remove = model.remove(BpmnMetaModelConstants.E_ATTR_TIME_CYCLE);
					if(remove != null && remove instanceof String){
						String body = (String) remove;
						EObjectWrapper<EClass, EObject> formalExpression = getModelController().createFormalExpression(body, "boolean", BpmnCoreConstants.BPMN_EXPRESSION_LANGUAGE_XPATH);
						model.put(BpmnMetaModelConstants.E_ATTR_TIME_CYCLE, formalExpression.getEInstance());
					}
					
					modelController.updateEmfModel(EObjectWrapper.wrap(eObject), model);
				}
			}
		}
		
		if (model.containsKey(BpmnMetaModelConstants.E_ATTR_TIME_DATE) ) {
			EList<EObject> listAttribute = elementWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_EVENT_DEFINITIONS);
			if(listAttribute.size() > 0){
				EObject eObject = listAttribute.get(0);
				if(BpmnModelClass.TIMER_EVENT_DEFINITION.isSuperTypeOf(eObject.eClass())){
					Object remove = model.remove(BpmnMetaModelConstants.E_ATTR_TIME_DATE);
					if(remove != null && remove instanceof String){
						String body = (String) remove;
						EObjectWrapper<EClass, EObject> formalExpression = getModelController().createFormalExpression(body, "boolean", BpmnCoreConstants.BPMN_EXPRESSION_LANGUAGE_XPATH);
						model.put(BpmnMetaModelConstants.E_ATTR_TIME_DATE, formalExpression.getEInstance());
					}
					
					modelController.updateEmfModel(EObjectWrapper.wrap(eObject), model);
				}
			}
		}

		return props;
	}
	
	protected void processForAttachedResource() {
		if(updateList.containsKey(BpmnMetaModelExtensionConstants.E_ATTR_EVENT) ){
			EObjectWrapper<EClass, EObject> addDataExtensionValueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(elementWrapper.getEInstance());
			String value = addDataExtensionValueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_EVENT);
			String path = (String)updateList.get(BpmnMetaModelExtensionConstants.E_ATTR_EVENT);
			if(value == null ||! value.equalsIgnoreCase(path)){
				String[] split = path.split("/");
				String name = split[split.length -1];
				EObjectWrapper<EClass, EObject> itemdef = getModelController().getItemDefinitionUsingEntity( path, false);
				if(elementWrapper.getEClassType().equals(BpmnModelClass.START_EVENT) ||
						elementWrapper.getEClassType().equals(BpmnModelClass.END_EVENT)){
					// check for message event
//					EList<EObject> listAttribute = elementWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_EVENT_DEFINITIONS);
//					if(listAttribute.size() > 0){
//						EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(listAttribute.get(0));
						EClass extType = (EClass)getExtendedType();
						if(extType != null && extType.equals(BpmnModelClass.MESSAGE_EVENT_DEFINITION)){
//							EObject message = wrap.getAttribute(BpmnMetaModelConstants.E_ATTR_MESSAGE_REF);
							EObjectWrapper<EClass, EObject> msgWrapper = null;
							if(/*message == null && */ itemdef != null){
								msgWrapper = getModelController().createMessage(name, itemdef);
								updateList
								.put(BpmnMetaModelConstants.E_ATTR_MESSAGE_REF,
										msgWrapper.getEInstance());
//								wrap.setAttribute(BpmnMetaModelConstants.E_ATTR_MESSAGE_REF, msgWrapper.getEInstance());
							}else{
//								wrap.setAttribute(BpmnMetaModelConstants.E_ATTR_MESSAGE_REF, null);
								updateList.put(BpmnMetaModelConstants.E_ATTR_MESSAGE_REF,null);
							}
							if (itemdef != null)
								updateList
										.put(BpmnMetaModelConstants.E_ATTR_STRUCTURE_REF,
												itemdef.getEInstance());
							
						}
//					}
				}
				
				if(BpmnModelClass.CATCH_EVENT.isSuperTypeOf(elementWrapper.getEClassType())){
					if (itemdef == null) {
						updateList
								.put(BpmnMetaModelConstants.E_ATTR_DATA_OUTPUT,
										null);
					} else {
						EObjectWrapper<EClass, EObject> createDataOutPut = getModelController()
								.createDataOutPut(name, itemdef);

						createDataOutPut.setAttribute(
								BpmnMetaModelConstants.E_ATTR_ITEM_SUBJECT_REF,
								itemdef.getEInstance());
						updateList.put(
								BpmnMetaModelConstants.E_ATTR_DATA_OUTPUT,
								createDataOutPut.getEInstance());
					}
					
					updateList.put(BpmnMetaModelConstants.E_ATTR_DATA_OUTPUT_ASSOCIATION,new ArrayList<EObject>());
				}
				
				if(BpmnModelClass.THROW_EVENT.isSuperTypeOf(elementWrapper.getEClassType())){
					if (itemdef == null) {
						updateList.put(
								BpmnMetaModelConstants.E_ATTR_DATA_INPUT, null);
					} else {
						EObjectWrapper<EClass, EObject> createDataInput = getModelController()
								.createDataInput(name, itemdef);

						createDataInput.setAttribute(
								BpmnMetaModelConstants.E_ATTR_ITEM_SUBJECT_REF,
								itemdef.getEInstance());
						updateList.put(
								BpmnMetaModelConstants.E_ATTR_DATA_INPUT,
								createDataInput.getEInstance());
					}
					
					updateList.put(BpmnMetaModelConstants.E_ATTR_DATA_INPUT_ASSOCIATION, new ArrayList<EObject>());
				}
			}else{
				updateList.remove(BpmnMetaModelExtensionConstants.E_ATTR_EVENT);
			}

		}
		
		if(updateList.containsKey(BpmnMetaModelExtensionConstants.E_ATTR_OUTPUT_MAP_XSLT)
				||updateList.containsKey(BpmnMetaModelExtensionConstants.E_ATTR_INPUT_MAP_XSLT)){
			EObjectWrapper<EClass, EObject> modelRoot = getModelController().getModelRoot();
			Collection<EObject> flowNodes = BpmnModelUtils.getFlowNodes(modelRoot, BpmnModelClass.DATA_OBJECT);
			EObjectWrapper<EClass, EObject> dataObjectWrap = null;
			if(!flowNodes.isEmpty()){
				dataObjectWrap = EObjectWrapper.wrap(flowNodes.iterator().next());
			}
			if(elementWrapper.getEClassType().equals(BpmnModelClass.START_EVENT) ||
					elementWrapper.getEClassType().equals(BpmnModelClass.END_EVENT)){
				EObjectWrapper<EClass, EObject> addDataExtensionValueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(elementWrapper.getEInstance());
				String value = addDataExtensionValueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_EVENT);
				if(!elementWrapper.getEClassType().equals(BpmnModelClass.START_EVENT) && (value == null ||value.trim().isEmpty())){
					updateList.remove(BpmnMetaModelExtensionConstants.E_ATTR_OUTPUT_MAP_XSLT);
					updateList.remove(BpmnMetaModelExtensionConstants.E_ATTR_INPUT_MAP_XSLT);
				}else{
					if (BpmnModelClass.CATCH_EVENT.isSuperTypeOf(elementWrapper
							.getEClassType())) {
						String expression = (String) updateList
								.get(BpmnMetaModelExtensionConstants.E_ATTR_OUTPUT_MAP_XSLT);
						EObject attribute = elementWrapper
								.getAttribute(BpmnMetaModelConstants.E_ATTR_DATA_OUTPUT);
						EObjectWrapper<EClass, EObject> sourceWrap = null;
						if(attribute != null)
							sourceWrap = EObjectWrapper.wrap(attribute);
						
						EObjectWrapper<EClass, EObject> associationWrapper = getModelController()
								.createOutputDataAssociation(sourceWrap,
										dataObjectWrap, expression);
						ArrayList<EObject> list = new ArrayList<EObject>();
						list.add(associationWrapper.getEInstance());

						EObjectWrapper<EClass, EObject> tranformation = EObjectWrapper.wrap((EObject)associationWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_TRANSFORMATION));
						tranformation.setAttribute(
								BpmnMetaModelConstants.E_ATTR_BODY, expression);
						updateList
								.put(BpmnMetaModelConstants.E_ATTR_DATA_OUTPUT_ASSOCIATION,
										list);
					}
					
					if (BpmnModelClass.THROW_EVENT.isSuperTypeOf(elementWrapper
							.getEClassType())) {
						String expression = (String) updateList
								.get(BpmnMetaModelExtensionConstants.E_ATTR_INPUT_MAP_XSLT);
						EObject attribute = elementWrapper
								.getAttribute(BpmnMetaModelConstants.E_ATTR_DATA_INPUT);
						EObjectWrapper<EClass, EObject> associationWrapper = getModelController()
								.createInputDataAssociation(dataObjectWrap,
										EObjectWrapper.wrap(attribute), expression);
						ArrayList<EObject> list = new ArrayList<EObject>();
						list.add(associationWrapper.getEInstance());

						EObjectWrapper<EClass, EObject> tranformation = EObjectWrapper.wrap((EObject)associationWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_TRANSFORMATION));
						tranformation.setAttribute(
								BpmnMetaModelConstants.E_ATTR_BODY, expression);
						updateList
								.put(BpmnMetaModelConstants.E_ATTR_DATA_INPUT_ASSOCIATION,
										list);
					}
				}
				
			}
		}
				
	}

}
