package com.tibco.cep.bpmn.core.codegen;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.core.codegen.temp.FlowNodeGenerator;
import com.tibco.cep.bpmn.core.utils.BpmnModelUtils;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.rule.RuleFunction;
import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.kernel.model.rule.Rule;
import com.tibco.cep.studio.core.util.MutableUtils;
import com.tibco.cep.studio.parser.codegen.CodeGenContext;

/**
 * @author pdhar
 *
 */
public class StartEventGenerator extends AbstractFlowElementGenerator implements
		BaseGenerator {


	public StartEventGenerator(EObject startEvent,BaseGenerator parent, CodeGenContext ctx,
			IProgressMonitor monitor, boolean overwrite) {
		super(startEvent,parent, ctx, monitor, overwrite);
	}
	
	
	public EObject getStartEvent() {
		return getFlowElement();
	}
	
	@Override
	public String generateElement(RuleFunction invokeFunction) throws Exception {
		EObjectWrapper<EClass, EObject> eventWrapper = EObjectWrapper.wrap(getStartEvent());
		EObjectWrapper<EClass, EObject> processWrapper = EObjectWrapper.wrap(getRootGenerator().getProcess());
		Symbol jobSymbol = BpmnModelUtils.convertBpmnSymbol(getRootGenerator().getJobSymbol());
		String processName = processWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
		// create trigger event
//		final String eventName = eventWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
//		final String startEventName = BpmnModelUtils.generatedEventName(getContext(), eventName);
//		Event event = createEvent(eventWrapper);
//		EObject eventBpmnSymbol = getSymbolMap(startEvent).addSymbol(event.getName(), event.getFullPath());
		// create post rtc execution rule
//		final String folderPath = getRuleFolderPath();
//		final String startRuleName = BpmnModelUtils.generatedRuleName(getContext(), processName)+"_"+startEventName+"_"+LAUNCHER_SUFFIX;
//		com.tibco.cep.designtime.core.model.rule.Rule rule = MutableUtils.createRule(
//				getProject().getName(), 
//				folderPath,
//				folderPath, 
//				startRuleName,
//				false);
//		rule.setPriority(Rule.DEFAULT_PRIORITY);
//		
//		// add rule scope
//		rule.getSymbols().getSymbolMap().put(jobSymbol.getIdName(), jobSymbol);
//		rule.getSymbols().getSymbolMap().put(eventSymbol.getIdName(), eventSymbol);
//		rule.setConditionText(jobSymbol.getIdName()+"@extId == "+eventSymbol.getIdName()+"@extId;");
//		
//		// set rule action
//		
//		
//		
//		rule.setActionText("");
//		
//		// save the rule
//		URI uri = BpmnModelUtils.getEntityFileURI(getProject(),rule);
//		MutableUtils.createRuleFile(rule, uri);
		return "";
	}

	

}
