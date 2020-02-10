package com.tibco.cep.bpmn.core.codegen.temp.inline;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.be.model.functions.impl.ModelFunction;
import com.tibco.cep.bpmn.core.Messages;
import com.tibco.cep.bpmn.core.codegen.CodegenError;
import com.tibco.cep.bpmn.core.codegen.temp.AbstractBpmnGenerator;
import com.tibco.cep.bpmn.core.utils.BpmnModelUtils;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.model.designtime.utils.WrappedObjectVisitor;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.ModelUtilsCore;
import com.tibco.cep.studio.parser.codegen.CodeGenContext;

public class EndEventGenerator<C extends EClass, O extends EObject> 
		extends AbstractBpmnGenerator<C, O>
		implements WrappedObjectVisitor<C, O> {

	private StringBuilder buffer;
	private Symbol eventSymbol;


	public EndEventGenerator(AbstractBpmnGenerator<C, O> parent,
			CodeGenContext ctx, IProgressMonitor monitor, boolean overwrite,StringBuilder sb) {
		super(parent, ctx, monitor, overwrite);
		this.buffer = sb;
	}
	
	public StringBuilder getBuffer() {
		return buffer;
	}
	
	
	@Override
	public boolean visit(EObjectWrapper<C, O> objWrapper) {
		try {
			this.buffer = new StringBuilder();
			final String eventName = objWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
			final String endEventName = BpmnModelUtils.generatedEventName(getContext(), eventName);
			Event event = createEvent(objWrapper);
			this.eventSymbol = addSymbol(event.getName(),event.getFullPath());
			final String eventURI = eventSymbol.getType();
			ModelFunction emf = BpmnModelUtils.findModelFunction(event);
			String signatureFormat = emf.signatureFormat();
			Map<String, Symbol> symbolMap = getScopeSymbolMap();
			List<Symbol> syms = findSymbol("jobDataConcept");
			if(syms.size() == 1 ) {
				String usage = MessageFormat.format(signatureFormat, syms.get(0).getIdName()+"@extId","null");
				getBuffer().append(ModelUtilsCore.convertPathToPackage(eventURI)+" endEvent = "+usage+";\n");
				getBuffer().append("Event.assertEvent(endEvent);");
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
