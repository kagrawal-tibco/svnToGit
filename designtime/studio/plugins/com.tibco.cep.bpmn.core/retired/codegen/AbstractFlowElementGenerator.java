package com.tibco.cep.bpmn.core.codegen;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.core.utils.BpmnModelUtils;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.BpmnCommonModelUtils;
import com.tibco.cep.designtime.core.model.rule.RuleFunction;
import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.studio.core.util.MutableUtils;
import com.tibco.cep.studio.parser.codegen.CodeGenContext;

public abstract class AbstractFlowElementGenerator extends AbstractGenerator {
	
	protected EObject flowElement;

	public AbstractFlowElementGenerator(EObject flowElement,BaseGenerator parent, CodeGenContext ctx,
			IProgressMonitor monitor, boolean overwrite) {
		super(parent, ctx, monitor, overwrite);
		this.flowElement = flowElement;
	}
	
	public EObject getFlowElement() {
		return flowElement;
	}
	
	public abstract String generateElement(RuleFunction invokeFunction) throws Exception; 
	
	@Override
	public void generate() throws Exception {
		if(BpmnModelClass.SEQUENCE_FLOW.isSuperTypeOf(getFlowElement().eClass())){
			createEvalFunction(getFlowElement());
		}
		if(BpmnModelClass.FLOW_NODE.isSuperTypeOf(getFlowElement().eClass())) {
			RuleFunction rf = createInvokeFunction(getFlowElement());		
			String id = BpmnModelUtils.getFlowElementId(getFlowElement());
			//createConstantFunction(id);
			getRootGenerator().getFlowElementHandlerMap().put(id,rf);
		}
	}
	
	
	protected RuleFunction createConstantFunction(String flowElementId) throws Exception {
		final String ruleFunctionName = flowElementId.toUpperCase().replace('.', '_');
		final String ruleFnfolderPath = getConstantsFolderPath();
		
		RuleFunction launchRuleFunction = MutableUtils.createRulefunction(getProject().getName(), 
				ruleFnfolderPath, ruleFnfolderPath, ruleFunctionName, false);
		launchRuleFunction.setActionText("return \""+flowElementId+"\";");
		launchRuleFunction.setReturnType("String");
		URI suri = BpmnModelUtils.getEntityFileURI(getProject(),launchRuleFunction);
		MutableUtils.createRuleFunctionFile(launchRuleFunction, suri);
		return launchRuleFunction;
	}
	
	
	protected RuleFunction createEvalFunction(EObject flowElement) throws Exception {
		final String flowElementName = BpmnModelUtils.getFlowElementId(flowElement).replace('.', '_');
		final String ruleFunctionName = BpmnCommonModelUtils.FLOW_ELEMENT_EVAL_PREFIX+BpmnModelUtils.generatedRulefunctionName(getContext(), flowElementName);
		final String ruleFnfolderPath = getRulefunctionFolderPath();
		
		Symbol bpmnContextSymbol = getRootGenerator().getBpmnContextSymbol();
		RuleFunction evalRuleFunction = MutableUtils.createRulefunction(getProject().getName(), 
				ruleFnfolderPath, ruleFnfolderPath, ruleFunctionName, false);
		evalRuleFunction.setReturnType("boolean");
		evalRuleFunction.getSymbols().getSymbolList().add(bpmnContextSymbol);
		String taskCode = generateElement(evalRuleFunction);
		if(taskCode != null && !taskCode.isEmpty()) {
			evalRuleFunction.setActionText(taskCode);
		} else {
			evalRuleFunction.setActionText("return true;");
		}

		URI suri = BpmnModelUtils.getEntityFileURI(getProject(),evalRuleFunction);
		MutableUtils.createRuleFunctionFile(evalRuleFunction, suri);
		return evalRuleFunction;
	}

	protected RuleFunction createInvokeFunction(EObject flowElement) throws Exception {
		final String flowElementName = BpmnModelUtils.getFlowElementId(flowElement).replace('.', '_');
		Symbol bpmnContextSymbol = getRootGenerator().getBpmnContextSymbol();
		
		final String ruleFunctionName = BpmnCommonModelUtils.FLOW_ELEMENT_HANDLER_PREFIX+BpmnModelUtils.generatedRulefunctionName(getContext(), flowElementName);
		final String ruleFnfolderPath = getRulefunctionFolderPath();
		
		RuleFunction launchRuleFunction = MutableUtils.createRulefunction(getProject().getName(), 
				ruleFnfolderPath, ruleFnfolderPath, ruleFunctionName, false);
		launchRuleFunction.getSymbols().getSymbolList().add(bpmnContextSymbol);
		String taskCode = generateElement(launchRuleFunction);
		if(taskCode != null) {
			launchRuleFunction.setActionText(taskCode);
		}
		
//		StringBuilder sb = new StringBuilder();
//		String rfnURI = ExtensionHelper.getExtensionAttributeValue(ruleFnTaskWrapper, BpmnMetaModelExtensionConstants.E_ATTR_RULEFUNCTION);
//		DesignerElement element = IndexUtils.getElement(getProject().getName(), rfnURI);
//		if(element != null && element instanceof RuleElement) {
//			RuleElement relement = (RuleElement) element;
//			ModelFunction emf = BpmnModelUtils.findModelFunction(relement.getRule());
//			String signatureFormat = emf.signatureFormat();
//			sb.append(signatureFormat+";");
//		}		
//		launchRuleFunction.setActionText(sb.toString());
		URI suri = BpmnModelUtils.getEntityFileURI(getProject(),launchRuleFunction);
		MutableUtils.createRuleFunctionFile(launchRuleFunction, suri);
		return launchRuleFunction;
	}

}
