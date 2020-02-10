package com.tibco.cep.bpmn.core.codegen.temp;

import java.text.MessageFormat;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.be.model.functions.impl.ModelFunction;
import com.tibco.cep.bpmn.core.codegen.temp.inline.EndEventGenerator;
import com.tibco.cep.bpmn.core.codegen.temp.inline.RulefunctionTaskGenerator;
import com.tibco.cep.bpmn.core.utils.BpmnModelUtils;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModel;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.model.designtime.utils.WrappedObjectVisitor;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.studio.core.util.ModelUtilsCore;
import com.tibco.cep.studio.parser.codegen.CodeGenContext;

public class InlineFlowNodeGenerator<C extends EClass, O extends EObject> 
		extends AbstractBpmnGenerator<C, O>
		implements BaseGenerator<C, O>,WrappedObjectVisitor<C,O> {

	private StringBuilder buffer;


	public InlineFlowNodeGenerator(AbstractBpmnGenerator<C, O> parent,
			CodeGenContext ctx, IProgressMonitor monitor, boolean overwrite) {
		super(parent, ctx, monitor, overwrite);
	}
	
	public StringBuilder getBuffer() {
		return buffer;
	}
	
	
	@Override
	public boolean visit(EObjectWrapper<C, O> objWrapper) {
		try {
			if(objWrapper.isInstanceOf(BpmnMetaModel.RULE_FUNCTION_TASK)) {
				RulefunctionTaskGenerator<C,O> rfnGenerator 
				= new RulefunctionTaskGenerator<C,O>(this,getContext(),getMonitor(),canOverwrite());
				objWrapper.accept(rfnGenerator);
				this.buffer = rfnGenerator.getBuffer();
			} else if(objWrapper.isInstanceOf(BpmnMetaModel.END_EVENT)) {
				EndEventGenerator<C,O> endEventGenerator 
				= new EndEventGenerator<C,O>(this,getContext(),getMonitor(),canOverwrite(),buffer);
				objWrapper.accept(endEventGenerator);
				this.buffer = endEventGenerator.getBuffer();
			}
			List<EObject> nextFlowNode = BpmnModelUtils.getNextFlowNodes(objWrapper.getEInstance());
			for(EObject fn:nextFlowNode) {
				EObjectWrapper<EClass, EObject> fnWrapper = EObjectWrapper.wrap(fn);
				FlowNodeGenerator<C,O> fnGenerator = new FlowNodeGenerator<C,O>(this,getContext(),getMonitor(),canOverwrite());
				fnWrapper.accept(fnGenerator);
				if(fnGenerator.isInline()) {
					this.buffer.append(fnGenerator.getBuffer());
				} else {
					this.buffer.append(fnGenerator.getBuffer());
					Event triggerEvent = fnGenerator.getTriggerEvent();
					Symbol eventSymbol = addSymbol(triggerEvent.getName(),triggerEvent.getFullPath());
					StringBuilder sb = new StringBuilder();
					ModelFunction emf = BpmnModelUtils.findModelFunction(triggerEvent);
					String signatureFormat = emf.signatureFormat();
					List<Symbol> symbols = findSymbol("jobDataConcept");
					if(symbols.size() == 1) {
						String usage = MessageFormat.format(signatureFormat, symbols.get(0).getIdName()+"@extId","null");
						sb.append(ModelUtilsCore.convertPathToPackage(triggerEvent.getFullPath())+" triggerEvent = "+usage+";\n");
						sb.append("Event.assertEvent(startEvent);\n");
						this.buffer.append(sb);
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}
	
	
}
