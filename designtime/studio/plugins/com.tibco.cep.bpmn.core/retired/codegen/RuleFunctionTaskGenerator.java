package com.tibco.cep.bpmn.core.codegen;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.be.model.functions.impl.ModelFunction;
import com.tibco.cep.bpmn.core.utils.BpmnModelUtils;
import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.designtime.core.model.rule.RuleFunction;
import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.MutableUtils;
import com.tibco.cep.studio.parser.codegen.CodeGenContext;

/**
 * @author pdhar
 *
 */
public class RuleFunctionTaskGenerator extends AbstractFlowElementGenerator {

	

	public RuleFunctionTaskGenerator(EObject rulefunction,BaseGenerator parent, CodeGenContext ctx,
			IProgressMonitor monitor, boolean overwrite) {
		super(rulefunction,parent, ctx, monitor, overwrite);
	}
	
	public EObject getRuleFunctionTask() {
		return getFlowElement();
	}
	
	@Override
	public String generateElement(RuleFunction invokeFunction) throws Exception {
		EObjectWrapper<EClass, EObject> ruleFnTaskWrapper = EObjectWrapper.wrap(getFlowElement());
		StringBuilder sb = new StringBuilder();
		String rfnURI = ExtensionHelper.getExtensionAttributeValue(ruleFnTaskWrapper, BpmnMetaModelExtensionConstants.E_ATTR_RULEFUNCTION);
		DesignerElement element = IndexUtils.getElement(getProject().getName(), rfnURI);
		if(element != null && element instanceof RuleElement) {
			RuleElement relement = (RuleElement) element;
			ModelFunction emf = BpmnModelUtils.findModelFunction(relement.getRule());
			String signatureFormat = emf.signatureFormat();
			sb.append(signatureFormat+";");
		}
		return sb.toString();
	}

	public void xgenerate() throws Exception {
		EObjectWrapper<EClass, EObject> ruleFnTaskWrapper = EObjectWrapper.wrap(getRuleFunctionTask());
		String ruleFnTaskName = ruleFnTaskWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
		Symbol jobSymbol = BpmnModelUtils.convertBpmnSymbol(getRootGenerator().getJobSymbol());
		final String ruleFunctionName = ""+BpmnModelUtils.generatedRulefunctionName(getContext(), ruleFnTaskName);
		final String ruleFnfolderPath = getRulefunctionFolderPath();
		
		RuleFunction launchRuleFunction = MutableUtils.createRulefunction(getProject().getName(), 
				ruleFnfolderPath, ruleFnfolderPath, ruleFunctionName, false);
		
		
		launchRuleFunction.getSymbols().getSymbolMap().put(jobSymbol.getIdName(),jobSymbol);
		StringBuilder sb = new StringBuilder();
		String rfnURI = ExtensionHelper.getExtensionAttributeValue(ruleFnTaskWrapper, BpmnMetaModelExtensionConstants.E_ATTR_RULEFUNCTION);
		DesignerElement element = IndexUtils.getElement(getProject().getName(), rfnURI);
		if(element != null && element instanceof RuleElement) {
			RuleElement relement = (RuleElement) element;
			ModelFunction emf = BpmnModelUtils.findModelFunction(relement.getRule());
			String signatureFormat = emf.signatureFormat();
			sb.append(signatureFormat+";");
		}		
		launchRuleFunction.setActionText(sb.toString());
		URI suri = BpmnModelUtils.getEntityFileURI(getProject(),launchRuleFunction);
		MutableUtils.createRuleFunctionFile(launchRuleFunction, suri);

	}

}
