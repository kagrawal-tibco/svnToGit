package com.tibco.cep.studio.wizard.as.internal.ui.tree;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.progress.IDeferredWorkbenchAdapter;
import org.eclipse.ui.progress.IElementCollector;

import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.wizard.as.ASPlugin;
import com.tibco.cep.studio.wizard.as.internal.utils.PluginUtils;

public class ASChannelElement extends AASElement implements IDeferredWorkbenchAdapter {

	@Override
	public ImageDescriptor getImageDescriptor(Object object) {
		if (object instanceof Channel) {
			return StudioUIPlugin.getImageDescriptor("icons/channel.png"); //$NON-NLS-1$
		}
		return null;
	}

	@Override
	public String getLabel(Object o) {
		if (o instanceof Channel) {
			Channel channel = (Channel) o;
			return channel.getName();
		}
		return null;
	}

	@Override
	public Object getParent(Object o) {
		return null;
	}

	@Override
	protected Object[] internalGetChildren(Object o, IProgressMonitor monitor) throws CoreException {
		return null;
	}

	@Override
	public void fetchDeferredChildren(Object object, IElementCollector collector, IProgressMonitor monitor) {
		// If it's not a folder, return an empty array
		if (!(object instanceof Channel)) {
			collector.add(new Object[0], monitor);
		}
		try {
			monitor = PluginUtils.monitorFor(monitor);
			monitor.beginTask("Fetching Spaces of " + getLabel(object), 100); //$NON-NLS-1$
			Channel channel = (Channel) object;
			FetchSpacesOperation operation = new FetchSpacesOperation(channel, collector);
			operation.run(PluginUtils.subMonitorFor(monitor, 100));
		}
		catch (InvocationTargetException e) {
			ASPlugin.openError(null, null, null, e);
		}
		catch (InterruptedException e) {
			// Cancelled by the user;
		}
		finally {
			monitor.done();
		}
	}

	@Override
	public boolean isContainer() {
		return true;
	}

	@Override
	public ISchedulingRule getRule(Object object) {
		return null;
	}

	private class FetchSpacesOperation implements IRunnableWithProgress {

		private Channel           channel;
		private IElementCollector collector;

		public FetchSpacesOperation(Channel channel, IElementCollector collector) {
			this.channel = channel;
			this.collector = collector;
		}

		@Override
		public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
			monitor = PluginUtils.monitorFor(monitor);
//			try {
//				monitor.beginTask("Fetching Spaces of " + channel.getName(), 100);
//				channel.getDriver().getChoice()
//				ISVNRemoteResource[] children = remoteFolder.members(Policy.subMonitorFor(monitor, 95));
//				collector.add(children, Policy.subMonitorFor(monitor, 5));
//			}
//			catch (CoreException cEx) {
//				throw SVNException.wrapException(e);
//			}
//			finally {
//				monitor.done();
//			}
		}

	}

}
