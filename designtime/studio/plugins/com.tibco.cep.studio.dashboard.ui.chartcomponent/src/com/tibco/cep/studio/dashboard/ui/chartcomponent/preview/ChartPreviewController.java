package com.tibco.cep.studio.dashboard.ui.chartcomponent.preview;

import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Display;

import com.tibco.cep.designtime.core.model.beviewsconfig.Component;
import com.tibco.cep.studio.dashboard.core.enums.InternalStatusEnum;
import com.tibco.cep.studio.dashboard.core.listeners.IMessageProvider;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalECoreFactory;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.BaseChartController;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.DashboardChartPlugin;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.model.LocalUnifiedComponent;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.preview.LocalPreviewDataElement.ENABLE;
import com.tibco.cep.studio.dashboard.ui.forms.DefaultExceptionHandler;

public class ChartPreviewController extends BaseChartController {

	private ChartPreviewBuilder chartPreviewBuilder;

	private ChartPreviewForm currChartPreviewForm;

	private DefaultExceptionHandler exceptionHandler;

	private LocalECoreFactory localECoreFactory;

	private Job previewGeneratorJob;

	public ChartPreviewController(ChartPreviewForm chartPreviewForm) {
		this.currChartPreviewForm = chartPreviewForm;
		exceptionHandler = new DefaultExceptionHandler(DashboardChartPlugin.PLUGIN_ID);
	}

	@Override
	public void setOriginalElement(LocalUnifiedComponent originalElement) {
		if (this.originalElement == originalElement) {
			// do nothing
		} else {
			try {
				if (this.originalElement != null) {
					if (chartPreviewBuilder != null) {
						// shutdown the current chart preview builder
						chartPreviewBuilder.dispose();
					}
				}
				super.setOriginalElement(originalElement);
				localECoreFactory = (LocalECoreFactory) this.originalElement.getRoot();
				chartPreviewBuilder = new ChartPreviewBuilder(localECoreFactory);
				if (currChartPreviewForm != null) {
					// schedule a preview job if currChartPreviewForm is not null
					refreshPreview(true);
				}
			} catch (Exception e) {
				String elementIdentifier = originalElement.getFolder() + originalElement.getName() + ".chart";
				String message = "could not switch chart preview processing to " + elementIdentifier;
				exceptionHandler.log(new Status(IStatus.ERROR, exceptionHandler.getPluginId(), message, e));
				disablePreview(false, message);
			}
		}
	}

	public void setChartPreviewForm(ChartPreviewForm chartPreviewForm) {
		this.currChartPreviewForm = chartPreviewForm;
		if (chartPreviewBuilder == null) {
			disablePreview(false, "Disabled");
			return;
		}
		String completeXML = chartPreviewBuilder.getCompleteXML();
		if (completeXML == null) {
			refreshPreview(true);
		} else {
			this.currChartPreviewForm.setInput(new LocalPreviewDataElement(localECoreFactory, completeXML));
		}
	}

	@Override
	protected void processElementAddition(IMessageProvider parent, IMessageProvider newElement) {
		if (chartPreviewBuilder != null && chartPreviewBuilder.isPreviewable(((LocalElement)parent).getElementType(), newElement.getProviderType(), InternalStatusEnum.StatusNew) == true) {
			refreshPreview(chartPreviewBuilder.regenerateData(newElement.getProviderType()));
		}
	}

	@Override
	protected void processElementRemoval(IMessageProvider parent, IMessageProvider removedElement) {
		if (chartPreviewBuilder != null && chartPreviewBuilder.isPreviewable(((LocalElement)parent).getElementType(), removedElement.getProviderType(), InternalStatusEnum.StatusRemove) == true) {
			refreshPreview(chartPreviewBuilder.regenerateData(removedElement.getProviderType()));
		}
	}

	@Override
	protected List<Status> processPropertyChange(IMessageProvider provider, SynProperty property, Object oldValue, Object newValue) {
		if (chartPreviewBuilder != null && chartPreviewBuilder.isPreviewable(property.getParent().getElementType(), property.getName(), InternalStatusEnum.StatusModified) == true) {
			refreshPreview(chartPreviewBuilder.regenerateData(property.getName()));
		}
		return Collections.emptyList();
	}

	public void refreshPreview(boolean rebuilddata) {
		if (chartPreviewBuilder == null) {
			disablePreview(false, "Disabled");
			return;
		}
		if (Display.getCurrent() != null) {
			if (previewGeneratorJob != null) {
				previewGeneratorJob.cancel();
			}
			previewGeneratorJob = new PreviewGeneratorJob(rebuilddata, Display.getCurrent());
			previewGeneratorJob.schedule();
		}
	}

	public void disablePreview(boolean permanent, String message) {
		if (currChartPreviewForm == null) {
			return;
		}
		if (permanent == true) {
			currChartPreviewForm.setInput(new LocalPreviewDataElement(localECoreFactory, ENABLE.NEVER, message));
		}
		else {
			currChartPreviewForm.setInput(new LocalPreviewDataElement(localECoreFactory, ENABLE.NO, message));
		}
	}

	@Override
	public void dispose() {
		if (chartPreviewBuilder != null) {
			chartPreviewBuilder.dispose();
		}
		currChartPreviewForm = null;
		super.dispose();
	}

	class PreviewGeneratorJob extends Job {

		private boolean rebuilddata;

		private Display display;

		public PreviewGeneratorJob(boolean rebuilddata, Display display) {
			super("Chart Preview Generator");
			setPriority(Job.SHORT);
			setSystem(true);
			this.rebuilddata = rebuilddata;
			this.display = display;
		}

		@Override
		protected IStatus run(final IProgressMonitor monitor) {
			try {
				//processUpdates = false;
				if (monitor.isCanceled() == true) {
					return Status.CANCEL_STATUS;
				}
				LocalElement chartSyncedForPreview = synchronize();
				if (monitor.isCanceled() == true) {
					return Status.CANCEL_STATUS;
				}
				chartPreviewBuilder.build((Component) chartSyncedForPreview.getEObject(), rebuilddata);
				if (monitor.isCanceled() == true) {
					return Status.CANCEL_STATUS;
				}

				display.asyncExec(new Runnable() {

					@Override
					public void run() {
						if (monitor.isCanceled() == true) {
							return;
						}
						currChartPreviewForm.setInput(new LocalPreviewDataElement(localECoreFactory, chartPreviewBuilder.getCompleteXML()));
					}

				});
				return Status.OK_STATUS;
			} catch (InterruptedException e) {
				return Status.CANCEL_STATUS;
			} catch (Exception e) {
				Status status = new Status(IStatus.WARNING, exceptionHandler.getPluginId(), "could not generate preview for " + originalElementIdentifier, e);
				exceptionHandler.log(status);
				return status;
			} finally {
				//processUpdates = true;
			}

		}

	}
}