package com.tibco.cep.bpmn.core.codegen.temp.offline;

import java.text.MessageFormat;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.be.model.functions.impl.ModelFunction;
import com.tibco.cep.bpmn.core.Messages;
import com.tibco.cep.bpmn.core.codegen.CodegenError;
import com.tibco.cep.bpmn.core.codegen.temp.AbstractBpmnGenerator;
import com.tibco.cep.bpmn.core.codegen.temp.FlowNodeGenerator;
import com.tibco.cep.bpmn.core.utils.BpmnModelUtils;
import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.model.designtime.utils.WrappedObjectVisitor;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.TIMEOUT_UNITS;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.rule.RuleFunction;
import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.kernel.model.rule.Rule;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.ModelUtilsCore;
import com.tibco.cep.studio.core.util.MutableUtils;
import com.tibco.cep.studio.parser.codegen.CodeGenContext;

public class StartEventGenerator<C extends EClass, O extends EObject> extends AbstractBpmnGenerator<C,O> implements WrappedObjectVisitor<C,O> {

	private Event event;

	public StartEventGenerator(AbstractBpmnGenerator<C,O> parent,
			CodeGenContext ctx, IProgressMonitor monitor, boolean overwrite) {
		super(parent, ctx, monitor, overwrite);
		// TODO Auto-generated constructor stub
	}
	
	public Event getEvent() {
		return event;
	}

	@Override
	public  boolean visit(
			EObjectWrapper<C, O> sevWrapper) {
		try{
			
			EObjectWrapper<C, O> processWrapper = getRootGenerator().getProcessWrapper();
			Symbol jobSymbol = getRootGenerator().getJobSymbol();
			String processName = processWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
			final String eventName = sevWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
			final String startEventName = BpmnModelUtils.generatedEventName(getContext(), eventName);
			this.event = createEvent(sevWrapper);
			Symbol eventSymbol = addSymbol(event.getName(), event.getFullPath());
			final String folderPath = getRuleFolderPath();
			final String startRuleName = BpmnModelUtils.generatedRuleName(getContext(), processName)+"_"+startEventName+"_"+LAUNCHER_SUFFIX;
			com.tibco.cep.designtime.core.model.rule.Rule rule = MutableUtils.createRule(
					getProject().getName(), 
					folderPath,
					folderPath, 
					startRuleName,
					false);
			rule.setPriority(Rule.DEFAULT_PRIORITY);
			rule.getSymbols().getSymbolMap().put(jobSymbol.getIdName(), jobSymbol);
			rule.getSymbols().getSymbolMap().put(eventSymbol.getIdName(), eventSymbol);
			rule.setConditionText(jobSymbol.getIdName()+"@extId == "+eventSymbol.getIdName()+"@extId;");
			StringBuilder buffer = new StringBuilder();
			List<EObject> nextFlowNode = BpmnModelUtils.getNextFlowNodes(sevWrapper.getEInstance());
			if(nextFlowNode.size() > 0) {
				
				for(EObject fn:nextFlowNode) {
					EObjectWrapper<EClass, EObject> fnWrapper = EObjectWrapper.wrap(fn);
					FlowNodeGenerator<C,O> flowNodeGenerator 
					= new FlowNodeGenerator<C,O>(this,getContext(),getMonitor(),canOverwrite());
					fnWrapper.accept(flowNodeGenerator);					
				}
				rule.setActionText(buffer.toString());
//				rule.setActionText("System.debugOut(\"Hello World\\n\");");
				
			}
			// save the rule
			URI uri = BpmnModelUtils.getEntityFileURI(getProject(),rule);
			MutableUtils.createRuleFile(rule, uri);
			
			return false;
		} catch (Exception e) {
			CodegenError cgErr = new CodegenError(Messages
					.getString("bpmn.codegen.error"), //$NON-NLS-1$
					CodegenError.MODEL_TYPE, e);
			reportError(cgErr);
			return false;
		}
	}
	
	
	
	

}
