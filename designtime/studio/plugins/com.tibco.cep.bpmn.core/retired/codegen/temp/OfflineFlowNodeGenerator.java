package com.tibco.cep.bpmn.core.codegen.temp;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.core.Messages;
import com.tibco.cep.bpmn.core.codegen.CodegenError;
import com.tibco.cep.bpmn.core.codegen.temp.inline.EndEventGenerator;
import com.tibco.cep.bpmn.core.codegen.temp.inline.RulefunctionTaskGenerator;
import com.tibco.cep.bpmn.core.utils.BpmnModelUtils;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModel;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.model.designtime.utils.WrappedObjectVisitor;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.kernel.model.rule.Rule;
import com.tibco.cep.studio.core.util.MutableUtils;
import com.tibco.cep.studio.parser.codegen.CodeGenContext;

public class OfflineFlowNodeGenerator<C extends EClass, O extends EObject> 
		extends AbstractBpmnGenerator<C, O>
		implements WrappedObjectVisitor<C, O> {

	private Event triggerEvent;


	public OfflineFlowNodeGenerator(AbstractBpmnGenerator<C, O> parent,
			CodeGenContext ctx, IProgressMonitor monitor, boolean overwrite) {
		super(parent, ctx, monitor, overwrite);
	}
	
	
	
	
	@Override
	public boolean visit(EObjectWrapper<C, O> objWrapper) {
		try {
			EObjectWrapper<C, O> processWrapper = getRootGenerator().getProcessWrapper();
			Symbol jobSymbol = getRootGenerator().getJobSymbol();
			String processName = processWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
			// create trigger event
			final String eventName = objWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
			final String startEventName = BpmnModelUtils.generatedEventName(getContext(), eventName);
			Event event = createEvent(objWrapper);
			this.triggerEvent = event;
			Symbol eventSymbol = addSymbol(event.getName(), event.getFullPath());
			// create post rtc execution rule
			final String folderPath = getRuleFolderPath();
			final String startRuleName = BpmnModelUtils.generatedRuleName(getContext(), processName)+"_"+startEventName+"_"+LAUNCHER_SUFFIX;
			com.tibco.cep.designtime.core.model.rule.Rule rule = MutableUtils.createRule(
					getProject().getName(), 
					folderPath,
					folderPath, 
					startRuleName,
					false);
			rule.setPriority(Rule.DEFAULT_PRIORITY);
			
			// add rule scope
			rule.getSymbols().getSymbolMap().put(jobSymbol.getIdName(), jobSymbol);
			rule.getSymbols().getSymbolMap().put(eventSymbol.getIdName(), eventSymbol);
			rule.setConditionText(jobSymbol.getIdName()+"@extId == "+eventSymbol.getIdName()+"@extId;");
			
			// set rule action
			StringBuilder buffer = new StringBuilder();
			// move to the next flow node
			List<EObject> nextFlowNode = BpmnModelUtils.getNextFlowNodes(objWrapper.getEInstance());
			for(EObject fn:nextFlowNode) {
				EObjectWrapper<EClass, EObject> fnWrapper = EObjectWrapper.wrap(fn);
				FlowNodeGenerator<C,O> fnGenerator = new FlowNodeGenerator<C,O>(this,getContext(),getMonitor(),canOverwrite());
				fnWrapper.accept(fnGenerator);
				if(fnGenerator.isInline()) {
					buffer.append(fnGenerator.getBuffer());
				}
			}
			
			
			rule.setActionText(buffer.toString());
			
			// save the rule
			URI uri = BpmnModelUtils.getEntityFileURI(getProject(),rule);
			MutableUtils.createRuleFile(rule, uri);
			
			
			
		} catch (Exception e) {
			CodegenError cgErr = new CodegenError(Messages
					.getString("bpmn.codegen.error"), //$NON-NLS-1$
					CodegenError.MODEL_TYPE, e);
			reportError(cgErr);
		}
		return false;
	}




	public Event getTriggerEvent() {
		return this.triggerEvent;
	}
	
	
}
