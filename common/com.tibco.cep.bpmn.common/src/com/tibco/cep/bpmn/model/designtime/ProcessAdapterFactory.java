/**
 * 
 */
package com.tibco.cep.bpmn.model.designtime;

import java.lang.reflect.Constructor;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.ontology.ProcessAdapter;
import com.tibco.cep.bpmn.model.designtime.ontology.TaskModelAdapter;
import com.tibco.cep.bpmn.model.designtime.ontology.TransitionModelAdapter;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.model.designtime.utils.ROEObjectWrapper;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.ModelAdapterFactory;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.process.BaseModelType;
import com.tibco.cep.designtime.model.process.ProcessModel;

/**
 * @author pdhar
 *
 */
public class ProcessAdapterFactory implements ModelAdapterFactory {
	
	public static ProcessAdapterFactory INSTANCE = new ProcessAdapterFactory();

	/**
	 * 
	 */
	private ProcessAdapterFactory() {
	}
	

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.ModelAdapterFactory#createAdapter(org.eclipse.emf.ecore.EObject, com.tibco.cep.designtime.model.Ontology, java.lang.Object[])
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <OE extends Entity, NE extends EObject> OE createAdapter(NE entity,
			Ontology emfOntology, Object... params)  {
		
		OE adapter = null;
		
		try {
			EObjectWrapper<EClass, EObject> wrapper = (EObjectWrapper<EClass, EObject>) EObjectWrapper.wrap(entity);
			
			if(wrapper.isInstanceOf(BpmnMetaModelConstants.PROCESS)) {
				Constructor<ProcessAdapter> constructor = 
						ProcessAdapter.class.getConstructor(EObject.class, Ontology.class,Object[].class);
				adapter = (OE)constructor.newInstance(entity, emfOntology,params);
			} 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return adapter;
	}
	
	
	@SuppressWarnings("unchecked")
	public <BE extends BaseModelType,NE extends EObject> BE createElementAdapter(ProcessModel processModel, NE element,Object... params) {
		ROEObjectWrapper<EClass, EObject> ew = (ROEObjectWrapper<EClass, EObject>) ROEObjectWrapper.wrap(element);
		EClass type = ew.getEClassType();
		if(BpmnModelClass.TASK.isSuperTypeOf(type) || 
				BpmnModelClass.EVENT.isSuperTypeOf(type) ||
				BpmnModelClass.GATEWAY.isSuperTypeOf(type)) {
			return (BE) new TaskModelAdapter(processModel,element,params);
			
		} else if (BpmnModelClass.SEQUENCE_FLOW.isSuperTypeOf(type)) {
			return (BE) new TransitionModelAdapter(processModel,element,params);
		}
		
		return null;
				
	}
	
//	@SuppressWarnings("unchecked")
//	public <BE extends BaseElement, NE extends EObject> BE createElementAdapter(
//			NE entity, AbstractOntologyAdapter<EObject> ontology, Object... params) {
//		if(entity == null)
//			return null;
//		
//		BE adapter = null;
//
//		try {
//			EObjectWrapper<EClass, EObject> wrapper = (EObjectWrapper<EClass, EObject>) EObjectWrapper
//					.wrap(entity);
//			if(BpmnModelClass.ACTIVITY.isSuperTypeOf(wrapper.getEClassType())){
//				adapter = createActivityAdapter(entity, ontology, params);
//			}else if(BpmnModelClass.GATEWAY.isSuperTypeOf(wrapper.getEClassType())){
//				adapter = createGatewayAdapter(entity, ontology, params);
//			}else if(BpmnModelClass.EVENT.isSuperTypeOf(wrapper.getEClassType())){
//				adapter = createEventAdapter(entity, ontology, params);
//			}else if(BpmnModelClass.EVENT_DEFINITION.isSuperTypeOf(wrapper.getEClassType())){
//				adapter = createEventDefinitionAdapter(entity, ontology, params);
//			}else if(BpmnModelClass.ARTIFACT.isSuperTypeOf(wrapper.getEClassType())){
//				adapter = createArtifactAdapter(entity, ontology, params);
//			}else if(BpmnModelClass.LOOP_CHARACTERISTICS.isSuperTypeOf(wrapper.getEClassType())){
//				adapter = createLoopCharacteristicsAdapter(entity, ontology, params);
//			}else if (wrapper.isInstanceOf(BpmnMetaModelConstants.LANE)) {
//				Constructor<LaneAdapter> constructor = LaneAdapter.class
//						.getConstructor(EObject.class,AbstractOntologyAdapter.class,Object[].class);
//				adapter = (BE) constructor.newInstance(entity, ontology,params);
//			} else if (wrapper.isInstanceOf(BpmnMetaModelConstants.LANE_SET)) {
//				Constructor<LaneSetAdapter> constructor = LaneSetAdapter.class
//						.getConstructor(EObject.class,AbstractOntologyAdapter.class,Object[].class);
//				adapter = (BE) constructor.newInstance(entity, ontology,params);
//			} else if (wrapper
//					.isInstanceOf(BpmnMetaModelConstants.SEQUENCE_FLOW)) {
//				Constructor<SequenceFlowAdapter> constructor = SequenceFlowAdapter.class
//						.getConstructor(EObject.class,AbstractOntologyAdapter.class,Object[].class);
//				adapter = (BE) constructor.newInstance(entity, ontology,params);
//			} else if (wrapper
//					.isInstanceOf(BpmnMetaModelConstants.FORMAL_EXPRESSION)) {
//				Constructor<FormalExpressionAdapter> constructor = FormalExpressionAdapter.class
//						.getConstructor(EObject.class,AbstractOntologyAdapter.class,Object[].class);
//				adapter = (BE) constructor.newInstance(entity, ontology,params);
//			} else if (wrapper
//					.isInstanceOf(BpmnMetaModelConstants.EXPRESSION)) {
//				Constructor<ExpressionAdapter> constructor = ExpressionAdapter.class
//						.getConstructor(EObject.class,AbstractOntologyAdapter.class,Object[].class);
//				adapter = (BE) constructor.newInstance(entity, ontology,params);
//			}else if (wrapper
//					.isInstanceOf(BpmnMetaModelConstants.COMPLEX_BEHAVIOR_DEFINITION)) {
//				Constructor<ComplexBehaviorDefinitionAdaptor> constructor = ComplexBehaviorDefinitionAdaptor.class
//						.getConstructor(EObject.class,AbstractOntologyAdapter.class,Object[].class);
//				adapter = (BE) constructor.newInstance(entity, ontology,params);
//			}else if (wrapper
//					.isInstanceOf(BpmnMetaModelConstants.ITEM_DEFINITION)) {
//				Constructor<ItemDefinitionAdaptor> constructor = ItemDefinitionAdaptor.class
//						.getConstructor(EObject.class,AbstractOntologyAdapter.class,Object[].class);
//				adapter = (BE) constructor.newInstance(entity, ontology,params);
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return adapter;
//	}
//	
//	@SuppressWarnings("unchecked")
//	private <BE extends BaseElement, NE extends EObject> BE createActivityAdapter(
//			NE entity, AbstractOntologyAdapter<EObject> ontology,
//			Object... params) {
//		BE adapter = null;
//		try {
//			EObjectWrapper<EClass, EObject> wrapper = (EObjectWrapper<EClass, EObject>) EObjectWrapper
//					.wrap(entity);
//			if (wrapper.isInstanceOf(BpmnMetaModelConstants.CALL_ACTIVITY)) {
//				Constructor<CallActivityAdapter> constructor = CallActivityAdapter.class
//						.getConstructor(EObject.class,AbstractOntologyAdapter.class,Object[].class);
//				adapter = (BE) constructor.newInstance(entity, ontology,params);
//			} else if (wrapper.isInstanceOf(BpmnMetaModelConstants.SUB_PROCESS)) {
//				Constructor<SubProcessAdapter> constructor = SubProcessAdapter.class
//						.getConstructor(EObject.class,AbstractOntologyAdapter.class,Object[].class);
//				adapter = (BE) constructor.newInstance(entity, ontology,params);
//			} else if (wrapper.isInstanceOf(BpmnMetaModelConstants.BUSINESS_RULE_TASK)) {
//				Constructor<BusinessRulesTaskAdapter> constructor = BusinessRulesTaskAdapter.class
//						.getConstructor(EObject.class,AbstractOntologyAdapter.class,Object[].class);
//				adapter = (BE) constructor.newInstance(entity, ontology,params);
//			}else if (wrapper.isInstanceOf(BpmnMetaModelConstants.DECISION_TABLE_TASK)) {
//				Constructor<DecisionTableTaskAdapter> constructor = DecisionTableTaskAdapter.class
//						.getConstructor(EObject.class,AbstractOntologyAdapter.class,Object[].class);
//				adapter = (BE) constructor.newInstance(entity, ontology,params);
//			}else if (wrapper.isInstanceOf(BpmnMetaModelConstants.RULE_FUNCTION_TASK)) {
//				Constructor<RuleFunctionTaskAdapter> constructor = RuleFunctionTaskAdapter.class
//						.getConstructor(EObject.class,AbstractOntologyAdapter.class,Object[].class);
//				adapter = (BE) constructor.newInstance(entity, ontology,params);
//			}else if (wrapper.isInstanceOf(BpmnMetaModelConstants.SEND_TASK)) {
//				Constructor<SendTaskAdapter> constructor = SendTaskAdapter.class
//						.getConstructor(EObject.class,AbstractOntologyAdapter.class,Object[].class);
//				adapter = (BE) constructor.newInstance(entity, ontology,params);
//			}else if (wrapper.isInstanceOf(BpmnMetaModelConstants.RECEIVE_TASK)) {
//				Constructor<RecieveTaskAdapter> constructor = RecieveTaskAdapter.class
//						.getConstructor(EObject.class,AbstractOntologyAdapter.class,Object[].class);
//				adapter = (BE) constructor.newInstance(entity, ontology,params);
//			}else if (wrapper.isInstanceOf(BpmnMetaModelConstants.SERVICE_TASK)) {
//				Constructor<ServiceTaskAdapter> constructor = ServiceTaskAdapter.class
//						.getConstructor(EObject.class,AbstractOntologyAdapter.class,Object[].class);
//				adapter = (BE) constructor.newInstance(entity, ontology,params);
//			}else if (wrapper.isInstanceOf(BpmnMetaModelConstants.SCRIPT_TASK)) {
//				Constructor<ScriptTaskAdapter> constructor = ScriptTaskAdapter.class
//						.getConstructor(EObject.class,AbstractOntologyAdapter.class,Object[].class);
//				adapter = (BE) constructor.newInstance(entity, ontology,params);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return adapter;
//
//	}
//	
//	@SuppressWarnings("unchecked")
//	private <BE extends BaseElement, NE extends EObject> BE createGatewayAdapter(
//			NE entity, AbstractOntologyAdapter<EObject> ontology,
//			Object... params) {
//		BE adapter = null;
//		try {
//			EObjectWrapper<EClass, EObject> wrapper = (EObjectWrapper<EClass, EObject>) EObjectWrapper
//					.wrap(entity);
//			if (wrapper.isInstanceOf(BpmnMetaModelConstants.COMPLEX_GATEWAY)) {
//				Constructor<ComplexGatewayAdapter> constructor = ComplexGatewayAdapter.class
//						.getConstructor(EObject.class,AbstractOntologyAdapter.class,Object[].class);
//				adapter = (BE) constructor.newInstance(entity, ontology,params);
//			} else if (wrapper.isInstanceOf(BpmnMetaModelConstants.INCLUSIVE_GATEWAY)) {
//				Constructor<InclusiveGatewayAdapter> constructor = InclusiveGatewayAdapter.class
//						.getConstructor(EObject.class,AbstractOntologyAdapter.class,Object[].class);
//				adapter = (BE) constructor.newInstance(entity, ontology,params);
//			} else if (wrapper.isInstanceOf(BpmnMetaModelConstants.EXCLUSIVE_GATEWAY)) {
//				Constructor<ExclusiveGateway> constructor = ExclusiveGateway.class
//						.getConstructor(EObject.class,AbstractOntologyAdapter.class,Object[].class);
//				adapter = (BE) constructor.newInstance(entity, ontology,params);
//			}else if (wrapper.isInstanceOf(BpmnMetaModelConstants.EVENT_BASED_GATEWAY)) {
//				Constructor<EventBasedGatewayAdapter> constructor = EventBasedGatewayAdapter.class
//						.getConstructor(EObject.class,AbstractOntologyAdapter.class,Object[].class);
//				adapter = (BE) constructor.newInstance(entity, ontology,params);
//			}else if (wrapper.isInstanceOf(BpmnMetaModelConstants.PARALLEL_GATEWAY)) {
//				Constructor<ParallelGatewayAdapter> constructor = ParallelGatewayAdapter.class
//						.getConstructor(EObject.class,AbstractOntologyAdapter.class,Object[].class);
//				adapter = (BE) constructor.newInstance(entity, ontology,params);
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//
//		return adapter;
//
//	}
//	
//	@SuppressWarnings("unchecked")
//	private <BE extends BaseElement, NE extends EObject> BE createEventAdapter(
//			NE entity, AbstractOntologyAdapter<EObject> ontology,
//			Object... params) {
//		BE adapter = null;
//		try {
//			EObjectWrapper<EClass, EObject> wrapper = (EObjectWrapper<EClass, EObject>) EObjectWrapper
//					.wrap(entity);
//			if (wrapper.isInstanceOf(BpmnMetaModelConstants.BOUNDARY_EVENT)) {
//				Constructor<BoundaryEventAdapter> constructor = BoundaryEventAdapter.class
//						.getConstructor(EObject.class,AbstractOntologyAdapter.class,Object[].class);
//				adapter = (BE) constructor.newInstance(entity, ontology,params);
//			} else if (wrapper.isInstanceOf(BpmnMetaModelConstants.START_EVENT)) {
//				Constructor<StartEventAdapter> constructor = StartEventAdapter.class
//						.getConstructor(EObject.class, AbstractOntologyAdapter.class,Object[].class);
//				adapter = (BE) constructor.newInstance(entity, ontology,params);
//				
//				
//			} else if (wrapper.isInstanceOf(BpmnMetaModelConstants.END_EVENT)) {
//				Constructor<EndEventAdapter> constructor = EndEventAdapter.class
//						.getConstructor(EObject.class, AbstractOntologyAdapter.class,Object[].class);
//				adapter = (BE) constructor.newInstance(entity, ontology,params);
//			}else if (wrapper.isInstanceOf(BpmnMetaModelConstants.INTERMEDIATE_THROW_EVENT)) {
//				Constructor<IntermediateThrowEventAdapter> constructor = IntermediateThrowEventAdapter.class
//						.getConstructor(EObject.class, AbstractOntologyAdapter.class,Object[].class);
//				adapter = (BE) constructor.newInstance(entity, ontology,params);
//			}else if (wrapper.isInstanceOf(BpmnMetaModelConstants.INTERMEDIATE_CATCH_EVENT)) {
//				Constructor<IntermediateCatchEventAdapter> constructor = IntermediateCatchEventAdapter.class
//						.getConstructor(EObject.class,AbstractOntologyAdapter.class,Object[].class);
//				adapter = (BE) constructor.newInstance(entity, ontology,params);
//			}else if (wrapper.isInstanceOf(BpmnMetaModelConstants.IMPLICIT_THROW_EVENT)) {
//				Constructor<ImplicitThrowEventAdapter> constructor = ImplicitThrowEventAdapter.class
//						.getConstructor(EObject.class,AbstractOntologyAdapter.class,Object[].class);
//				adapter = (BE) constructor.newInstance(entity, ontology,params);
//			}
//		} catch (Exception e) {
//		e.printStackTrace();
//		}
//
//		return adapter;
//
//	}
//	
//	@SuppressWarnings("unchecked")
//	private <BE extends BaseElement, NE extends EObject> BE createEventDefinitionAdapter(
//			NE entity, AbstractOntologyAdapter<EObject> ontology,
//			Object... params) {
//		BE adapter = null;
//		try {
//			EObjectWrapper<EClass, EObject> wrapper = (EObjectWrapper<EClass, EObject>) EObjectWrapper
//					.wrap(entity);
//			if (wrapper.isInstanceOf(BpmnMetaModelConstants.MESSAGE_EVENT_DEFINITION)) {
//				Constructor<MessageEventDefinitionAdapter> constructor = MessageEventDefinitionAdapter.class
//						.getConstructor(EObject.class,AbstractOntologyAdapter.class,Object[].class);
//				adapter = (BE) constructor.newInstance(entity, ontology,params);
//			} else if (wrapper.isInstanceOf(BpmnMetaModelConstants.ERROR_EVENT_DEFINITION)) {
//				Constructor<ErrorEventDefinitionAdapter> constructor = ErrorEventDefinitionAdapter.class
//						.getConstructor(EObject.class,AbstractOntologyAdapter.class,Object[].class);
//				adapter = (BE) constructor.newInstance(entity, ontology,params);
//			} else if (wrapper.isInstanceOf(BpmnMetaModelConstants.TIMER_EVENT_DEFINITION)) {
//				Constructor<TimerEventDefinitionAdapter> constructor = TimerEventDefinitionAdapter.class
//						.getConstructor(Ontology.class,AbstractOntologyAdapter.class,Object[].class);
//				adapter = (BE) constructor.newInstance(entity, ontology,params);
//			}else if (wrapper.isInstanceOf(BpmnMetaModelConstants.SIGNAL_EVENT_DEFINITION)) {
//				Constructor<SignalEventDefinitionAdapter> constructor = SignalEventDefinitionAdapter.class
//						.getConstructor(Ontology.class,AbstractOntologyAdapter.class,Object[].class);
//				adapter = (BE) constructor.newInstance(entity, ontology,params);
//			}else if (wrapper.isInstanceOf(BpmnMetaModelConstants.CANCEL_EVENT_DEFINITION)) {
//				Constructor<CancelEventDefinitionAdapter> constructor = CancelEventDefinitionAdapter.class
//						.getConstructor(Ontology.class,AbstractOntologyAdapter.class,Object[].class);
//				adapter = (BE) constructor.newInstance(entity, ontology,params);
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//
//		return adapter;
//
//	}
//	
//	@SuppressWarnings("unchecked")
//	private <BE extends BaseElement, NE extends EObject> BE createArtifactAdapter(
//			NE entity, AbstractOntologyAdapter<EObject> ontology,
//			Object... params) {
//		BE adapter = null;
//		try {
//			EObjectWrapper<EClass, EObject> wrapper = (EObjectWrapper<EClass, EObject>) EObjectWrapper
//					.wrap(entity);
//			if (wrapper.isInstanceOf(BpmnMetaModelConstants.TEXT_ANNOTATION)) {
//				Constructor<TextAnnotationAdapter> constructor = TextAnnotationAdapter.class
//						.getConstructor(EObject.class,AbstractOntologyAdapter.class,Object[].class);
//				adapter = (BE) constructor.newInstance(entity, ontology,params);
//			} else if (wrapper.isInstanceOf(BpmnMetaModelConstants.ASSOCIATION)) {
//				Constructor<AssociationAdapter> constructor = AssociationAdapter.class
//						.getConstructor(EObject.class,AbstractOntologyAdapter.class,Object[].class);
//				adapter = (BE) constructor.newInstance(entity, ontology,params);
//			} 
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//
//		return adapter;
//	}
//	
//	@SuppressWarnings("unchecked")
//	private <BE extends BaseElement, NE extends EObject> BE createLoopCharacteristicsAdapter(
//			NE entity, AbstractOntologyAdapter<EObject> ontology,
//			Object... params) {
//		BE adapter = null;
//		try {
//			EObjectWrapper<EClass, EObject> wrapper = (EObjectWrapper<EClass, EObject>) EObjectWrapper
//					.wrap(entity);
//			if (wrapper.isInstanceOf(BpmnMetaModelConstants.STANDARD_LOOP_CHARACTERISTICS)) {
//				Constructor<StandardLoopCharacteristicsAdaptor> constructor = StandardLoopCharacteristicsAdaptor.class
//						.getConstructor(EObject.class,AbstractOntologyAdapter.class,Object[].class);
//				adapter = (BE) constructor.newInstance(entity, ontology,params);
//			} else if (wrapper.isInstanceOf(BpmnMetaModelConstants.MULTI_INSTANCE_LOOP_CHARACTERISTICS)) {
//				Constructor<MultiInstanceLoopCharacteristicsAdaptor> constructor = MultiInstanceLoopCharacteristicsAdaptor.class
//						.getConstructor(EObject.class,AbstractOntologyAdapter.class,Object[].class);
//				adapter = (BE) constructor.newInstance(entity, ontology,params);
//			} 
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//
//		return adapter;
//
//	}
//	
//	public <BE extends ExtnBaseElement, NE extends EObject> BE createExtensionElementAdapter(NE entity,	Object... params){
//		BE adapter = null;
//		try {
//			EObjectWrapper<EClass, EObject> wrapper = (EObjectWrapper<EClass, EObject>) EObjectWrapper
//					.wrap(entity);
//			if (wrapper.isInstanceOf(BpmnMetaModelExtensionConstants.EXTN_PROPERTY_DEFINITION_DATA)) {
//				Constructor<PropertyDefinitionAdaptor> constructor = PropertyDefinitionAdaptor.class
//						.getConstructor(EObject.class, Object[].class);
//				adapter = (BE) constructor.newInstance(entity,params);
//			} 
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		return adapter;
//	}
			

}
