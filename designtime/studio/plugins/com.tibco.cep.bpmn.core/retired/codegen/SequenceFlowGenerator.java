package com.tibco.cep.bpmn.core.codegen;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.designtime.core.model.rule.RuleFunction;
import com.tibco.cep.studio.parser.codegen.CodeGenContext;

/**
 * @author pdhar
 *
 */
public class SequenceFlowGenerator extends AbstractFlowElementGenerator implements
		BaseGenerator {

	private EObject sequenceFlow;

	public SequenceFlowGenerator(EObject seqFlow,BaseGenerator parent, CodeGenContext ctx,
			IProgressMonitor monitor, boolean overwrite) {
		super(seqFlow,parent, ctx, monitor, overwrite);
		this.sequenceFlow = seqFlow;
	}
	
	
	public EObject getSequenceFlow() {
		return sequenceFlow;
	}
	
	@Override
	public String generateElement(RuleFunction invokeFunction) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
