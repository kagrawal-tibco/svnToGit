package com.tibco.cep.bpmn.ui.graph.model;

import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.ACTIVITY;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.AD_HOC_SUB_PROCESS;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.BOUNDARY_EVENT;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.BUSINESS_RULE_TASK;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.CALL_ACTIVITY;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.CATCH_EVENT;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.COMPLEX_GATEWAY;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.END_EVENT;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.ERROR_EVENT_DEFINITION;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.EVENT;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.EVENT_BASED_GATEWAY;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.EXCLUSIVE_GATEWAY;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.GATEWAY;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.INCLUSIVE_GATEWAY;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.INFERENCE_TASK;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.INTERMEDIATE_CATCH_EVENT;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.INTERMEDIATE_THROW_EVENT;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.JAVA_TASK;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.LANE;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.MANUAL_TASK;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.MESSAGE_EVENT_DEFINITION;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.PARALLEL_GATEWAY;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.PROCESS;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.RECEIVE_TASK;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.RULE_FUNCTION_TASK;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.SCRIPT_TASK;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.SEND_TASK;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.SEQUENCE_FLOW;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.SERVICE_TASK;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.SIGNAL_EVENT_DEFINITION;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.START_EVENT;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.SUB_PROCESS;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.TASK;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.TEXT_ANNOTATION;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.THROW_EVENT;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.TIMER_EVENT_DEFINITION;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.TRANSACTION;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModel;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EEnumWrapper;
import com.tibco.cep.bpmn.ui.Messages;
import com.tibco.cep.bpmn.ui.editor.BpmnLayoutManager;
import com.tibco.cep.bpmn.ui.editor.IBpmnDiagramManager;
import com.tibco.cep.bpmn.ui.graph.model.factory.common.TextAnnotationNodeUIFactory;
import com.tibco.cep.bpmn.ui.graph.model.factory.events.AbstractConnectorUIFactory;
import com.tibco.cep.bpmn.ui.graph.model.factory.events.BoundaryEventUIFactory;
import com.tibco.cep.bpmn.ui.graph.model.factory.events.EndEventNodeUIFactory;
import com.tibco.cep.bpmn.ui.graph.model.factory.events.IntermediateCatchEventNodeUIFactory;
import com.tibco.cep.bpmn.ui.graph.model.factory.events.IntermediateThrowEventNodeUIFactory;
import com.tibco.cep.bpmn.ui.graph.model.factory.events.StartEventNodeUIFactory;
import com.tibco.cep.bpmn.ui.graph.model.factory.gateway.ComplexGatewayNodeUIFactory;
import com.tibco.cep.bpmn.ui.graph.model.factory.gateway.EventBasedGatewayNodeUIFactory;
import com.tibco.cep.bpmn.ui.graph.model.factory.gateway.ExclusiveGatewayNodeUIFactory;
import com.tibco.cep.bpmn.ui.graph.model.factory.gateway.InclusiveGatewayNodeUIFactory;
import com.tibco.cep.bpmn.ui.graph.model.factory.gateway.ParallelGatewayNodeUIFactory;
import com.tibco.cep.bpmn.ui.graph.model.factory.process.PoolLaneNodeUIFactory;
import com.tibco.cep.bpmn.ui.graph.model.factory.process.ProcessNodeUIFactory;
import com.tibco.cep.bpmn.ui.graph.model.factory.process.activity.CallActivityNodeUIFactory;
import com.tibco.cep.bpmn.ui.graph.model.factory.process.activity.SubProcessNodeUIFactory;
import com.tibco.cep.bpmn.ui.graph.model.factory.process.activity.task.BusinessRuleTaskNodeUIFactory;
import com.tibco.cep.bpmn.ui.graph.model.factory.process.activity.task.InferenceTaskNodeUIFactory;
import com.tibco.cep.bpmn.ui.graph.model.factory.process.activity.task.JavaTaskNodeUIFactory;
import com.tibco.cep.bpmn.ui.graph.model.factory.process.activity.task.ManualTaskNodeUIFactory;
import com.tibco.cep.bpmn.ui.graph.model.factory.process.activity.task.RecieveTaskNodeUIFactory;
import com.tibco.cep.bpmn.ui.graph.model.factory.process.activity.task.RuleFunctionTaskNodeUIFactory;
import com.tibco.cep.bpmn.ui.graph.model.factory.process.activity.task.ScriptTaskNodeUIFactory;
import com.tibco.cep.bpmn.ui.graph.model.factory.process.activity.task.SendTaskNodeUIFactory;
import com.tibco.cep.bpmn.ui.graph.model.factory.process.activity.task.ServiceTaskNodeUIFactory;
import com.tibco.cep.bpmn.ui.graph.model.factory.sequence.AssociationEdgeFactory;
import com.tibco.cep.bpmn.ui.graph.model.factory.sequence.SequenceFlowEdgeFactory;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tomsawyer.graphicaldrawing.TSENode;


public class BpmnGraphUIFactory   {	
	
	private static BpmnGraphUIFactory instance;
	private BpmnLayoutManager layoutManager;
	
	private BpmnGraphUIFactory(BpmnLayoutManager layoutManager) {
		this.layoutManager = layoutManager;
	}
	
	public synchronized static BpmnGraphUIFactory getInstance(BpmnLayoutManager lManager) {
		if(instance == null) {
			instance = new BpmnGraphUIFactory(lManager);
		}else if(lManager != null && !(instance.layoutManager.getDiagramManager() instanceof IBpmnDiagramManager) && (lManager.getDiagramManager() instanceof IBpmnDiagramManager)){
			instance = new BpmnGraphUIFactory(lManager);
		}else if(lManager != null && (instance.layoutManager.getDiagramManager() != lManager.getDiagramManager())){
			instance = new BpmnGraphUIFactory(lManager);
		}
		return instance;
	}
	
	public BpmnLayoutManager getLayoutManager() {
		return layoutManager;
	}
	
	public AbstractEdgeUIFactory getEdgeUIFactory(String name,ExpandedName classSpec, Object ...extSpec ) {
		return getEdgeUIFactory(name,BpmnMetaModel.INSTANCE.getEClass(classSpec), extSpec);
	}
	
	public AbstractEdgeUIFactory getEdgeUIFactory(String name, EClass type, Object ...extSpec ){
		if(SEQUENCE_FLOW.isSuperTypeOf(type)) {
			if(name != null) {
				return  new SequenceFlowEdgeFactory(name, type, layoutManager); //$NON-NLS-1$
			} else {
				return  new SequenceFlowEdgeFactory(Messages.getString("title.sequence"), type, layoutManager); //$NON-NLS-1$
			}
		} else if( BpmnModelClass.ASSOCIATION.isSuperTypeOf(type)) {
			if(name != null) {
				return  new AssociationEdgeFactory(name, type, 
						layoutManager, BpmnModelClass.ENUM_ASSOCIATION_DIRECTION_NONE); //$NON-NLS-1$
			} else {
				return  new AssociationEdgeFactory(Messages.getString("title.association"),type, 
						layoutManager, BpmnModelClass.ENUM_ASSOCIATION_DIRECTION_NONE); //$NON-NLS-1$
			}
		}
		return null;
	}
	
	public AbstractNodeUIFactory getNodeUIFactory(String name,String referredBEResource, String toolId,ExpandedName classSpec, Object ... extSpec)  {
		return getNodeUIFactory(name,referredBEResource, toolId,BpmnMetaModel.INSTANCE.getEClass(classSpec), extSpec);
	}
	
	public AbstractNodeUIFactory getNodeUIFactory(String name,String referredBEResource, String toolId,EClass type, Object ... extSpec) {
		AbstractNodeUIFactory factory =  null;
		if(LANE.isSuperTypeOf(type)){
			if(extSpec != null &&  extSpec.length > 0) {
				
				final EClass extType = BpmnMetaModel.INSTANCE.getEClass((ExpandedName) extSpec[0]);
				if(extType != null) {
					if(PROCESS.isSuperTypeOf(extType)) {
						factory =  new PoolLaneNodeUIFactory(name,referredBEResource, toolId, layoutManager,true);
					} else {
						factory =  new PoolLaneNodeUIFactory(name,referredBEResource, toolId,layoutManager,false);
					}
				} else { // default is lane
					factory =  new PoolLaneNodeUIFactory(name,referredBEResource,  toolId,layoutManager,false);
				}
			} else { // default is lane
				factory =  new PoolLaneNodeUIFactory(name,referredBEResource,  toolId,layoutManager,false);
			}
		} else if(PROCESS.isSuperTypeOf(type)) {
			factory =  new ProcessNodeUIFactory(name,referredBEResource, toolId,layoutManager);
		} else if(ACTIVITY.isSuperTypeOf(type)) {
			if(SUB_PROCESS.isSuperTypeOf(type)){
				if(TRANSACTION.isSuperTypeOf(type)) {
				} else if(AD_HOC_SUB_PROCESS.isSuperTypeOf(type)) {
				} else {
				}
				boolean trigByEvent = false;
				if(extSpec != null &&  extSpec.length > 0){
					if (extSpec[0] != null)
						trigByEvent = (Boolean) extSpec[0];
				}
				factory =  new SubProcessNodeUIFactory(name,referredBEResource,  toolId,layoutManager,true, trigByEvent);
			} else if(CALL_ACTIVITY.isSuperTypeOf(type)) {
				factory = new CallActivityNodeUIFactory(name,referredBEResource,  toolId, layoutManager,true);
			}else if(TASK.isSuperTypeOf(type)) {
				factory = getTaskNodeFactory(name,referredBEResource, toolId, type, extSpec); 
			}
		} else if(EVENT.isSuperTypeOf(type)) {
			factory = getEventNodeFactory(name, referredBEResource, toolId, type, extSpec);
		} else if(GATEWAY.isSuperTypeOf(type)) {
			factory = getGatewayNodeFactory(name,referredBEResource,  toolId, type,extSpec);
		} else if(TEXT_ANNOTATION.isSuperTypeOf(type)) {
			factory = new TextAnnotationNodeUIFactory(name, referredBEResource, toolId, layoutManager);
		}
		return factory;
	}
	
	public AbstractConnectorUIFactory getConnectorUIFactory(TSENode parentNode, String name,String referredBEResource, String toolId,EClass type, Object ... extSpec) {
		AbstractConnectorUIFactory factory =  null;
		if(BOUNDARY_EVENT.isSuperTypeOf(type)) {
			if(extSpec != null && extSpec.length > 0) {
				final EClass extType = BpmnMetaModel.INSTANCE.getEClass((ExpandedName) extSpec[0]);
				if(MESSAGE_EVENT_DEFINITION.isSuperTypeOf(extType)){
					factory = new BoundaryEventUIFactory(parentNode, name, referredBEResource,  toolId,layoutManager, MESSAGE_EVENT_DEFINITION);
				} else if(TIMER_EVENT_DEFINITION.isSuperTypeOf(extType)){
					factory =  new BoundaryEventUIFactory(parentNode, name,referredBEResource,   toolId, layoutManager, TIMER_EVENT_DEFINITION);
				} if(ERROR_EVENT_DEFINITION.isSuperTypeOf(extType)){
					factory =  new BoundaryEventUIFactory(parentNode, name, referredBEResource, toolId, layoutManager,ERROR_EVENT_DEFINITION);
				}if(SIGNAL_EVENT_DEFINITION.isSuperTypeOf(extType)){
					factory =  new BoundaryEventUIFactory(parentNode, name, referredBEResource, toolId, layoutManager,SIGNAL_EVENT_DEFINITION);
				}						
			} else { // default
				factory =  new BoundaryEventUIFactory(parentNode, name,referredBEResource,  toolId, layoutManager,MESSAGE_EVENT_DEFINITION);
			}
		}
		return factory;
	}

	/**
	 * @param name 
	 * @param type
	 * @param extSpec
	 * @return
	 */
	private AbstractNodeUIFactory getGatewayNodeFactory(String name, String referredBEResource, String toolId, EClass type,
			Object ... extSpec) {
		if(INCLUSIVE_GATEWAY.isSuperTypeOf(type)) {
			return new InclusiveGatewayNodeUIFactory(name,  referredBEResource, toolId, layoutManager);
		} else if(EXCLUSIVE_GATEWAY.isSuperTypeOf(type)) {
			return new ExclusiveGatewayNodeUIFactory(name,  referredBEResource,  toolId, layoutManager);
		} else if(COMPLEX_GATEWAY.isSuperTypeOf(type)) {
			return new ComplexGatewayNodeUIFactory(name,  referredBEResource,  toolId, layoutManager);
		} else if(PARALLEL_GATEWAY.isSuperTypeOf(type)) {
			return new ParallelGatewayNodeUIFactory(name, referredBEResource, toolId, layoutManager);
		} else if(EVENT_BASED_GATEWAY.isSuperTypeOf(type)) {
			EEnumLiteral gatewayType = null;
			EEnumWrapper<EEnum, EEnumLiteral> enWrapper = EEnumWrapper.createInstance(BpmnMetaModel.ENUM_EVENT_BASED_GATEWAY_TYPE);
			if(extSpec.length > 0) {
				gatewayType = enWrapper.getEnumLiteral((ExpandedName) extSpec[0]);
			} else {
				gatewayType = enWrapper.getEnumLiteral(BpmnMetaModel.ENUM_EVENT_BASED_GATEWAY_TYPE_EXCLUSIVE);				
			}
			return new EventBasedGatewayNodeUIFactory(name,  referredBEResource, toolId, layoutManager, gatewayType);
		} 
		return null;
	}

	/**
	 * @param name 
	 * @param type
	 * @param extSpec
	 * @return
	 */
	private AbstractNodeUIFactory getTaskNodeFactory(String name,String referredBEResource, String toolId,EClass type	, Object ... extSpec) {
		if(JAVA_TASK.isSuperTypeOf(type)) {
			return new JavaTaskNodeUIFactory(name, referredBEResource, toolId,layoutManager);
		} else if(RULE_FUNCTION_TASK.isSuperTypeOf(type)){
			return new RuleFunctionTaskNodeUIFactory(name, referredBEResource, toolId,layoutManager);
		}else if(SCRIPT_TASK.isSuperTypeOf(type)){
			return new ScriptTaskNodeUIFactory(name, referredBEResource, toolId,layoutManager);
		} else if(SEND_TASK.isSuperTypeOf(type)) {
			return new SendTaskNodeUIFactory(name, referredBEResource, toolId, layoutManager);
		} else if(RECEIVE_TASK.isSuperTypeOf(type)) {
			return new RecieveTaskNodeUIFactory(name,referredBEResource,  toolId, layoutManager);
		} else if(MANUAL_TASK.isSuperTypeOf(type)) {
			return new ManualTaskNodeUIFactory(name, referredBEResource,toolId, layoutManager);
		} else if(BUSINESS_RULE_TASK.isSuperTypeOf(type)) {
			return new BusinessRuleTaskNodeUIFactory(name,referredBEResource,  toolId, layoutManager);
		} else if(SERVICE_TASK.isSuperTypeOf(type)) {
			return new ServiceTaskNodeUIFactory(name,referredBEResource,  toolId, layoutManager);
		} else if(INFERENCE_TASK.isSuperTypeOf(type)) {
			return new InferenceTaskNodeUIFactory(name, referredBEResource, toolId, layoutManager);
		}
		return null;
	}

	/**
	 * @param name 
	 * @param type
	 * @param extSpec
	 * @return
	 */
	private AbstractNodeUIFactory getEventNodeFactory(String name, String referredBEResource,String toolId, EClass type,Object... extSpec) {
		
		if(CATCH_EVENT.isSuperTypeOf(type)) {
			if(START_EVENT.isSuperTypeOf(type)) {
				if(extSpec != null && extSpec.length > 0) {
					final EClass extType = BpmnMetaModel.INSTANCE.getEClass((ExpandedName) extSpec[0]);
					if(extType == null) {
						return  new StartEventNodeUIFactory(name, referredBEResource,toolId, layoutManager, null);
					} else 	if(MESSAGE_EVENT_DEFINITION.isSuperTypeOf(extType)){
						return  new StartEventNodeUIFactory(name,referredBEResource,  toolId, layoutManager, MESSAGE_EVENT_DEFINITION);
					} else if(TIMER_EVENT_DEFINITION.isSuperTypeOf(extType)){
						return  new StartEventNodeUIFactory(name, referredBEResource, toolId, layoutManager, TIMER_EVENT_DEFINITION);
					} else if(SIGNAL_EVENT_DEFINITION.isSuperTypeOf(extType)){
						return  new StartEventNodeUIFactory(name, referredBEResource, toolId, layoutManager, SIGNAL_EVENT_DEFINITION);
					}
					
				} else { // default
					return  new StartEventNodeUIFactory(name,referredBEResource,  toolId, layoutManager, null);
				}
				
			} else if(INTERMEDIATE_CATCH_EVENT.isSuperTypeOf(type)) {
				if(extSpec != null && extSpec.length > 0) {
					final EClass extType = BpmnMetaModel.INSTANCE.getEClass((ExpandedName) extSpec[0]);
					if(MESSAGE_EVENT_DEFINITION.isSuperTypeOf(extType)){
						return new IntermediateCatchEventNodeUIFactory(name, referredBEResource, toolId,layoutManager, MESSAGE_EVENT_DEFINITION);
					} else if(TIMER_EVENT_DEFINITION.isSuperTypeOf(extType)){
						return new IntermediateCatchEventNodeUIFactory(name, referredBEResource, toolId, layoutManager, TIMER_EVENT_DEFINITION);
					} if(ERROR_EVENT_DEFINITION.isSuperTypeOf(extType)){
						return  new IntermediateCatchEventNodeUIFactory(name,referredBEResource,  toolId, layoutManager, ERROR_EVENT_DEFINITION);
					}if(SIGNAL_EVENT_DEFINITION.isSuperTypeOf(extType)){
						return  new IntermediateCatchEventNodeUIFactory(name,referredBEResource,  toolId, layoutManager, SIGNAL_EVENT_DEFINITION);
					}						
				} else { // default
					return  new IntermediateCatchEventNodeUIFactory(name,referredBEResource,  toolId, layoutManager, MESSAGE_EVENT_DEFINITION);
				}
			}
			
		} else if( THROW_EVENT.isSuperTypeOf(type)) {
			if(END_EVENT.isSuperTypeOf(type)) {
				if(extSpec != null && extSpec.length > 0) {
					final EClass extType = BpmnMetaModel.INSTANCE.getEClass((ExpandedName) extSpec[0]);
					if(extType == null) {
						return new EndEventNodeUIFactory(name,referredBEResource,  toolId, layoutManager, null);
					} else if(MESSAGE_EVENT_DEFINITION.isSuperTypeOf(extType)){
						return new EndEventNodeUIFactory(name,referredBEResource,  toolId, layoutManager, MESSAGE_EVENT_DEFINITION);
					}else if(ERROR_EVENT_DEFINITION.isSuperTypeOf(extType)){
						return new EndEventNodeUIFactory(name,referredBEResource,  toolId, layoutManager, ERROR_EVENT_DEFINITION);
					}else if(SIGNAL_EVENT_DEFINITION.isSuperTypeOf(extType)){
						return new EndEventNodeUIFactory(name,referredBEResource,  toolId, layoutManager, SIGNAL_EVENT_DEFINITION);
					}						
				} else {//default
					return new EndEventNodeUIFactory(name,referredBEResource, toolId, layoutManager, null);
				}
			} else if(INTERMEDIATE_THROW_EVENT.isSuperTypeOf(type)){
				if(extSpec != null && extSpec.length > 0 && extSpec[0] != null) {
					final EClass extType = BpmnMetaModel.INSTANCE.getEClass((ExpandedName) extSpec[0]);
					if(MESSAGE_EVENT_DEFINITION.isSuperTypeOf(extType)){
						return new IntermediateThrowEventNodeUIFactory(name,referredBEResource,  toolId,layoutManager, MESSAGE_EVENT_DEFINITION);
					}else if(SIGNAL_EVENT_DEFINITION.isSuperTypeOf(extType)){
						return new IntermediateThrowEventNodeUIFactory(name,referredBEResource,  toolId,layoutManager, SIGNAL_EVENT_DEFINITION);
					} 
				} else { // default
					return new IntermediateThrowEventNodeUIFactory(name,referredBEResource,  toolId, layoutManager, null);
				}
			}
		}
		return null;
	}
	


}
