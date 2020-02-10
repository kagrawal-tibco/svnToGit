package com.tibco.cep.bpmn.core.codegen;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.designtime.core.model.rule.RuleFunction;
import com.tibco.cep.studio.parser.codegen.CodeGenContext;

public class IntermediateCatchEventGenerator extends AbstractFlowElementGenerator
		implements BaseGenerator {
	
	

	public IntermediateCatchEventGenerator(EObject task, BaseGenerator parent,
			CodeGenContext ctx, IProgressMonitor monitor, boolean overwrite) {
		super(task, parent, ctx, monitor, overwrite);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String generateElement(RuleFunction invokeFunction) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
