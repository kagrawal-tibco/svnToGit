package com.tibco.cep.bpmn.core.codegen.temp.inline;

import java.text.MessageFormat;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.be.model.functions.impl.ModelFunction;
import com.tibco.cep.bpmn.core.Messages;
import com.tibco.cep.bpmn.core.codegen.CodegenError;
import com.tibco.cep.bpmn.core.codegen.temp.AbstractBpmnGenerator;
import com.tibco.cep.bpmn.core.utils.BpmnModelUtils;
import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModel;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.model.designtime.utils.WrappedObjectVisitor;
import com.tibco.cep.designtime.core.model.rule.Compilable;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.parser.codegen.CodeGenContext;

public class RulefunctionTaskGenerator<C extends EClass, O extends EObject> 
		extends AbstractBpmnGenerator<C, O>
		implements WrappedObjectVisitor<C, O> {

	private StringBuilder buffer;


	public RulefunctionTaskGenerator(AbstractBpmnGenerator<C, O> parent,
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
				this.buffer = new StringBuilder();
				String rulefnURI = ExtensionHelper.getExtensionAttributeValue(objWrapper, BpmnMetaModelExtensionConstants.E_ATTR_RULEFUNCTION);
				DesignerElement element = IndexUtils.getElement(getProject().getName(), rulefnURI);
				if(element != null && element instanceof RuleElement) {
					Compilable entity = ((RuleElement)element).getRule();
					ModelFunction emf = BpmnModelUtils.findModelFunction(entity);
					String signatureFormat = emf.signatureFormat();
					String usage = MessageFormat.format(signatureFormat, "null","null");
					getBuffer().append(usage+";\n");
				}
			}
			
		} catch (Exception e) {
			CodegenError cgErr = new CodegenError(Messages
					.getString("bpmn.codegen.error"), //$NON-NLS-1$
					CodegenError.MODEL_TYPE, e);
			reportError(cgErr);
		}
		return false;
	}
	
	
}
