package com.tibco.cep.studio.wizard.as.internal.ui.tree;

import static com.tibco.cep.studio.wizard.as.ASPlugin._FLAG_LOG_CORE_EXCEPTIONS;
import static com.tibco.cep.studio.wizard.as.ASPlugin._FLAG_LOG_OTHER_EXCEPTIONS;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.ui.model.IWorkbenchAdapter;
import org.eclipse.ui.progress.IDeferredWorkbenchAdapter;

import com.tibco.cep.studio.wizard.as.ASPlugin;

abstract public class AASElement implements IWorkbenchAdapter, IAdaptable {

	@Override
	public Object getAdapter(Class adapter) {
		Object adapted = null;
		if (adapter == IWorkbenchAdapter.class) {
			adapted = this;
		}
		if ((IDeferredWorkbenchAdapter.class == adapter) && this instanceof IDeferredWorkbenchAdapter) {
			adapted = this;
		}
		return adapted;
	}

	@Override
	public Object[] getChildren(final Object o) {
		try {
			final Object[][] children = new Object[1][];
			IRunnableWithProgress runnable = new IRunnableWithProgress() {
				public void run(IProgressMonitor monitor) throws InvocationTargetException {
					try {
						children[0] = internalGetChildren(o, monitor);
					}
					catch (CoreException cEx) {
						throw new InvocationTargetException(cEx);
					}
				}
			};
			ASPlugin.runWithProgress(null, runnable);
			return children[0];
		}
		catch (InterruptedException iEx) {
		}
		catch (InvocationTargetException itEx) {
			handle(itEx);
		}
		return new Object[0];
	}

	protected void handle(Throwable t) {
		ASPlugin.openError(null, null, null, t, _FLAG_LOG_CORE_EXCEPTIONS | _FLAG_LOG_OTHER_EXCEPTIONS);
	}

	protected abstract Object[] internalGetChildren(Object o, IProgressMonitor monitor) throws CoreException;

}
