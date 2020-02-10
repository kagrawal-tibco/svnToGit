package com.tibco.cep.studio.dashboard.ui.statemachinecomponent.wizard;

import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Composite;

import com.tibco.cep.studio.dashboard.core.enums.InternalStatusEnum;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalStateSeriesConfig;
import com.tibco.cep.studio.dashboard.core.listeners.IMessageProvider;
import com.tibco.cep.studio.dashboard.core.listeners.ISynElementChangeListener;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.util.StringUtil;
import com.tibco.cep.studio.dashboard.ui.forms.BaseForm;
import com.tibco.cep.studio.dashboard.ui.statemachinecomponent.DashboardStateMachineComponentPlugin;

public abstract class AbstractTemplateSettingsWizardPage extends WizardPage {

	public static enum TYPE {INDICATOR, CONTENT};

	private TYPE type;

	private BaseForm baseForm;

	private LocalStateSeriesConfig seriesConfig;

	private ISynElementChangeListener elementChangeListener;

	public AbstractTemplateSettingsWizardPage(TYPE type,String pageName, String title, ImageDescriptor titleImage) {
		super(pageName, title, titleImage);
		this.type = type;
	}

	@Override
	public void createControl(Composite parent) {
		baseForm = createForm(parent);
		baseForm.init();
		baseForm.disableAll();
		setControl(baseForm.getControl());
		validatePage();
	}

	protected abstract BaseForm createForm(Composite parent);

	public TYPE getType(){
		return type;
	}

	public final void setSeriesConfig(LocalStateSeriesConfig seriesConfig) {
		try  {
			if (elementChangeListener != null && this.seriesConfig != null) {
				unsubscribeAll(seriesConfig);
			}
			this.seriesConfig = seriesConfig;
			if (elementChangeListener == null) {
				elementChangeListener = new WizardPageValidationTriggeringElementChangeListener();
			}
			subscribeAll(seriesConfig);
			if (seriesConfig == null){
				baseForm.disableAll();
			}
			else {
				baseForm.enableAll();

				baseForm.setInput(seriesConfig);
				validatePage();
			}
			validatePage();
		} catch (Exception e) {
			String message = "could not initialize "+baseForm.getTitle().toLowerCase()+" settings...";
			IStatus status = new Status(IStatus.ERROR, DashboardStateMachineComponentPlugin.PLUGIN_ID, message, e);
			DashboardStateMachineComponentPlugin.getInstance().getLog().log(status);
			setMessage(message,ERROR);
			setPageComplete(false);
			return;
		}

	}

	public final LocalStateSeriesConfig getSeriesConfig(){
		return seriesConfig;
	}

	private void validatePage(){
		setMessage(null);
		boolean pageComplete = true;
		if (seriesConfig == null){
			setMessage("No "+baseForm.getTitle()+" will be created...", IStatus.WARNING);
		}
		else {
			try {
				pageComplete = seriesConfig.isValid();
				if (pageComplete == false){
					String message = StringUtil.join(seriesConfig.getValidationMessage().getReportableMessages(), "\n");
					setMessage(message,ERROR);
				}
			} catch (Exception e) {
				String message = "could not validate "+baseForm.getTitle().toLowerCase()+" settings...";
				IStatus status = new Status(IStatus.ERROR,DashboardStateMachineComponentPlugin.PLUGIN_ID,message,e);
				DashboardStateMachineComponentPlugin.getInstance().getLog().log(status);
				setMessage(message, IStatus.WARNING);
				pageComplete = false;
			}
		}
		setPageComplete(pageComplete);
	}


	private void unsubscribeAll(LocalElement localElement) {
		//unsubscribe from localElement
		localElement.unsubscribeAll(elementChangeListener);
		//go thru all children and unsubscribe
		List<LocalElement> allChildren = localElement.getAllChildren(false);
		for (LocalElement localChild : allChildren) {
			unsubscribeAll(localChild);
		}
	}

	private void subscribeAll(LocalElement localElement) {
		//subscribe from localElement
		localElement.subscribeToAll(elementChangeListener);
		//go thru all children and unsubscribe
		List<LocalElement> allChildren = localElement.getAllChildren(false);
		for (LocalElement localChild : allChildren) {
			subscribeAll(localChild);
		}
	}

	class WizardPageValidationTriggeringElementChangeListener implements ISynElementChangeListener {

		@Override
		public void elementAdded(IMessageProvider parent, IMessageProvider newElement) {
			validatePage();
			subscribeAll((LocalElement) newElement);
		}

		@Override
		public void elementChanged(IMessageProvider parent, IMessageProvider changedElement) {
			validatePage();
		}

		@Override
		public void elementRemoved(IMessageProvider parent, IMessageProvider removedElement) {
			validatePage();
			unsubscribeAll((LocalElement) removedElement);
		}

		@Override
		public void elementStatusChanged(IMessageProvider parent, InternalStatusEnum status) {
			validatePage();
		}

		@Override
		public String getName() {
			return "WizardPageValidationTriggeringElementChangeListener";
		}

		@Override
		public void propertyChanged(IMessageProvider provider, SynProperty property, Object oldValue, Object newValue) {
			validatePage();
		}

	}

}