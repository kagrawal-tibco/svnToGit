package com.tibco.cep.studio.dashboard.ui.chartcomponent;

import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

import com.tibco.cep.studio.dashboard.core.enums.InternalStatusEnum;
import com.tibco.cep.studio.dashboard.core.listeners.IMessageProvider;
import com.tibco.cep.studio.dashboard.core.listeners.ISynElementChangeListener;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.model.LocalUnifiedComponent;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.types.ChartType;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.types.ChartTypeRegistry;
import com.tibco.cep.studio.dashboard.ui.forms.ExceptionHandler;

public abstract class BaseChartController implements ExceptionHandler, ISynElementChangeListener {

	protected LocalUnifiedComponent originalElement;

	protected String originalElementIdentifier;

	protected String originalElementGUID;

//	protected ISynElementChangeListener elementChangeListener;

	public BaseChartController() {
		//elementChangeListener = new UnifiedComponentChangeListener();
	}

	public void setOriginalElement(LocalUnifiedComponent originalElement) throws Exception {
		if (this.originalElement != null) {
			unsubscribeAll(this.originalElement, this);
		}
		this.originalElement = originalElement;
		this.originalElementIdentifier = this.originalElement.getFolder() + this.originalElement.getName() + ".chart";
		this.originalElementGUID = originalElement.getID();
		// subscribe for all changes
		subscribeAll(this.originalElement, this);
	}

	protected void subscribeAll(LocalElement localElement, ISynElementChangeListener elementChangeListener) {
//		System.err.println(this.getClass().getSimpleName()+".subscribeAll("+localElement+","+elementChangeListener+")");
		//subscribe from localElement
		localElement.subscribeToAll(elementChangeListener);
		//go thru all children and subscribe
		List<LocalElement> allChildren = localElement.getAllChildren(false);
		for (LocalElement localChild : allChildren) {
			subscribeAll(localChild, elementChangeListener);
		}
	}

	protected void unsubscribeAll(LocalElement localElement, ISynElementChangeListener elementChangeListener) {
		//un-subscribe from localElement
		localElement.unsubscribeAll(elementChangeListener);
		//go thru all children and un-subscribe
		List<LocalElement> allChildren = localElement.getAllChildren(false);
		for (LocalElement localChild : allChildren) {
			unsubscribeAll(localChild, elementChangeListener);
		}
	}

	public LocalElement getOriginalElement() {
		return originalElement;
	}

	public String getOriginalElementGUID() {
		return originalElementGUID;
	}

	public LocalElement synchronize() throws Exception {
		// we have to synchronize the original element
		ChartType currChartType = ChartTypeRegistry.getInstance().get(originalElement.getPropertyValue(LocalUnifiedComponent.PROP_KEY_TYPE));
		LocalElement nativeElement = currChartType.getProcessor().createNativeElement(originalElement);
		if (nativeElement == null) {
			throw new Exception(currChartType.getName() + " could not create native element from " + originalElement);
		}
		if (nativeElement == originalElement) {
			throw new Exception("Native element is same as unified element");
		}
		if (nativeElement.getClass().equals(originalElement.getClass()) == true) {
			throw new Exception("Native element is same as unified element");
		}
		// update the guid on the native element
		nativeElement.setID(originalElementGUID);
		nativeElement.synchronize();
		return nativeElement;
	}

	protected abstract void processElementAddition(IMessageProvider parent, IMessageProvider newElement);

	protected abstract void processElementRemoval(IMessageProvider parent, IMessageProvider removedElement);

	protected abstract List<Status> processPropertyChange(IMessageProvider provider, SynProperty property, Object oldValue, Object newValue);

	@Override
	public String getPluginId() {
		return DashboardChartPlugin.PLUGIN_ID;
	}

	@Override
	public IStatus createStatus(int severity, String message, Throwable exception) {
		return new Status(severity, getPluginId(), message, exception);
	}

	@Override
	public void log(IStatus status) {
		DashboardChartPlugin.getDefault().getLog().log(status);
	}

	@Override
	public void logAndAlert(String title, IStatus status) {
		log(status);
		switch (status.getSeverity()) {
			case IStatus.OK:
			case IStatus.INFO:
				MessageDialog.openInformation(Display.getCurrent().getActiveShell(), title, status.getMessage());
				break;
			case IStatus.ERROR:
				MessageDialog.openError(Display.getCurrent().getActiveShell(), title, status.getMessage());
				break;
			case IStatus.WARNING:
				MessageDialog.openWarning(Display.getCurrent().getActiveShell(), title, status.getMessage());
				break;
		}
	}

	public void dispose() {
		if (originalElement != null) {
			try {
				unsubscribeAll(originalElement, this);
			} catch (Exception e) {
				log(new Status(IStatus.ERROR, getPluginId(), "could not dispose editing controller for " + originalElement, e));
			}
		}
	}

//	private class UnifiedComponentChangeListener implements ISynElementChangeListener {

		@Override
		public void elementAdded(IMessageProvider parent, IMessageProvider newElement) {
			processElementAddition(parent, newElement);
			subscribeAll((LocalElement) newElement, this);
		}

		@Override
		public void elementChanged(IMessageProvider parent, IMessageProvider changedElement) {
			// do nothing
		}

		@Override
		public void elementRemoved(IMessageProvider parent, IMessageProvider removedElement) {
			processElementRemoval(parent, removedElement);
			unsubscribeAll((LocalElement) removedElement, this);
		}

		@Override
		public void elementStatusChanged(IMessageProvider parent, InternalStatusEnum status) {
			// do nothing
		}

		@Override
		public String getName() {
			return "Chart Change Listener";
		}

		@Override
		public void propertyChanged(IMessageProvider provider, SynProperty property, Object oldValue, Object newValue) {
			System.out.println(getClass().getSimpleName()+".propertyChanged(" + provider + "," + property + "," + oldValue + "," + newValue + ")");
			// set the bulk mode on to prevent event propagation
			originalElement.setBulkOperation(true);
			try {
				List<Status> statuses = processPropertyChange(provider, property, oldValue, newValue);
				if (statuses.isEmpty() == false) {
					log(new MultiStatus(getPluginId(), IStatus.ERROR, statuses.toArray(new Status[statuses.size()]), "An exception occured while processing changes to " + property.getName() + " in "
							+ originalElement.getFullPath(), null));
				}
			} finally {
				originalElement.setBulkOperation(false);
			}
		}
//	}

}