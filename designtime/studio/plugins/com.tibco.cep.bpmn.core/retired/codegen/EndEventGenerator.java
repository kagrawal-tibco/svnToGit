package com.tibco.cep.bpmn.core.codegen;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.designtime.core.model.rule.RuleFunction;
import com.tibco.cep.studio.parser.codegen.CodeGenContext;

/**
 * @author pdhar
 *
 */
public class EndEventGenerator extends AbstractFlowElementGenerator implements
		BaseGenerator {


	public EndEventGenerator(EObject endEvent,BaseGenerator parent, CodeGenContext ctx,
			IProgressMonitor monitor, boolean overwrite) {
		super(endEvent, parent, ctx, monitor, overwrite);
	}
	
	
	public EObject getEndEvent() {
		return getFlowElement();
	}
	
	@Override
	public String generateElement(RuleFunction invokeFunction) throws Exception {
		return "";
	}
	
	
}
