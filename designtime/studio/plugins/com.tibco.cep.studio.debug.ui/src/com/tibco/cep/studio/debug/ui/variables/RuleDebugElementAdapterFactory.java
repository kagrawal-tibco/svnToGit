package com.tibco.cep.studio.debug.ui.variables;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.debug.internal.ui.viewers.model.provisional.IElementContentProvider;
import org.eclipse.debug.internal.ui.viewers.model.provisional.IElementLabelProvider;

import com.tibco.cep.studio.debug.core.model.RuleDebugStackFrame;
import com.tibco.cep.studio.debug.core.model.RuleDebugThread;
@SuppressWarnings({"rawtypes","restriction"})
public class RuleDebugElementAdapterFactory implements IAdapterFactory {
	
	private static final IElementContentProvider fgCPVariable = new RuleVariableContentProvider();
	private static final IElementContentProvider fgCPStackFrame = new RuleStackFrameContentProvider();
	private static final IElementLabelProvider fgLPVariable = new RuleVariableLabelProvider();
	private static final IElementLabelProvider fgLPStackFrame = new RuleStackFrameLabelProvider();
	private static final IElementContentProvider fgCPThread = new RuleThreadContentProvider();
	
	@Override
	public Object getAdapter(Object adaptableObject, Class adapterType) {
		if (IElementContentProvider.class.equals(adapterType)) {
			if(adaptableObject instanceof IVariable) {
				return fgCPVariable;
			} else if(adaptableObject instanceof RuleDebugStackFrame) {
				return fgCPStackFrame;
			} else if(adaptableObject instanceof RuleDebugThread) {
				return fgCPThread;
			}
		} else if(IElementLabelProvider.class.equals(adapterType)){
			if(adaptableObject instanceof IVariable) {
				return fgLPVariable;
			} else if(adaptableObject instanceof RuleDebugStackFrame) {
				return fgLPStackFrame;
			}			
		}
		return null;
	}

	@Override
	public Class[] getAdapterList() {
		return new Class[]{IElementContentProvider.class, IElementLabelProvider.class};
	}

}
