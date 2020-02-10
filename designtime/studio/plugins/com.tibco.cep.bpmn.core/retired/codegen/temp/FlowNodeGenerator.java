package com.tibco.cep.bpmn.core.codegen.temp;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModel;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.model.designtime.utils.WrappedObjectVisitor;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.studio.parser.codegen.CodeGenContext;

public class FlowNodeGenerator<C extends EClass, O extends EObject> 
		extends AbstractBpmnGenerator<C, O>
		implements WrappedObjectVisitor<C, O> {

	private StringBuilder buffer;
	private boolean inline = false;
	private Event triggerEvent = null;

	public FlowNodeGenerator(AbstractBpmnGenerator<C, O> parent,
			CodeGenContext ctx, IProgressMonitor monitor, boolean overwrite) {
		super(parent, ctx, monitor, overwrite);
	}
	
	public StringBuilder getBuffer() {
		return buffer;
	}
	
	public boolean isInline() {
		return inline;
	}
	
	public Event getTriggerEvent() {
		return triggerEvent;
	}
	
	
	@Override
	public boolean visit(EObjectWrapper<C, O> objWrapper) {
		if(objWrapper.isInstanceOf(BpmnMetaModel.ACTIVITY)) {
			this.inline = (Boolean) ExtensionHelper.getExtensionAttributeValue(objWrapper, BpmnMetaModelExtensionConstants.E_ATTR_INLINE);
			if(inline) {
				// the next node call will be in this action
				InlineFlowNodeGenerator<C,O> flowNodeGenerator 
				= new InlineFlowNodeGenerator<C,O>(this,getContext(),getMonitor(),canOverwrite());
				objWrapper.accept(flowNodeGenerator);
				this.buffer = flowNodeGenerator.getBuffer();
			} else {
				// the next node call will be in another rule triggered by another event
				// only the creation of the event and send event will happen here
				OfflineFlowNodeGenerator<C,O> flowNodeGenerator 
				= new OfflineFlowNodeGenerator<C,O>(this,getContext(),getMonitor(),canOverwrite());
				objWrapper.accept(flowNodeGenerator);
				this.triggerEvent = flowNodeGenerator.getTriggerEvent();
			}
		} else if(objWrapper.isInstanceOf(BpmnModelClass.START_EVENT)) { 
			OfflineFlowNodeGenerator<C,O> flowNodeGenerator 
			= new OfflineFlowNodeGenerator<C,O>(this,getContext(),getMonitor(),canOverwrite());
			objWrapper.accept(flowNodeGenerator);
			this.triggerEvent = flowNodeGenerator.getTriggerEvent();
		} else {
			// the next node call will be in this action
			InlineFlowNodeGenerator<C,O> flowNodeGenerator 
			= new InlineFlowNodeGenerator<C,O>(this,getContext(),getMonitor(),canOverwrite());
			objWrapper.accept(flowNodeGenerator);
			this.buffer = flowNodeGenerator.getBuffer();
		}
		
		return false;
	}
	
	
}
