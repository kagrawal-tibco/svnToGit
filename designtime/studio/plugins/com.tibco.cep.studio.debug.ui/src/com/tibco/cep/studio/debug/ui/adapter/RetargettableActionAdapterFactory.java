package com.tibco.cep.studio.debug.ui.adapter;


import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.debug.ui.actions.IToggleBreakpointsTarget;

/**
 * Creates adapters for retargettable actions in debug platform.
 * Contributed via <code>org.eclipse.core.runtime.adapters</code> 
 * extension point. 
 * 
 * @since 3.0
 */
@SuppressWarnings("rawtypes")
public class RetargettableActionAdapterFactory implements IAdapterFactory {
	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IAdapterFactory#getAdapter(java.lang.Object, java.lang.Class)
	 */
	public Object getAdapter(Object adaptableObject, Class adapterType) {
//		if (adapterType == IRunToLineTarget.class) {
//			return new RunToLineAdapter();
//		}
		if (adapterType == IToggleBreakpointsTarget.class) {
			return new RuleToggleBreakpointAdapter();
		} 
		return null;
	}
	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IAdapterFactory#getAdapterList()
	 */
	public Class[] getAdapterList() {
		return new Class[]{
//				IRunToLineTarget.class, 
				IToggleBreakpointsTarget.class};
	}
}
