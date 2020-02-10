package com.tibco.cep.studio.dashboard.ui.statemachinecomponent.editor.wizards;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import com.tibco.cep.designtime.core.model.states.State;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalDataSource;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalStateMachineComponent;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalStateSeriesConfig;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalStateVisualization;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.helpers.StateMachineComponentHelper;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.forms.BaseForm;
import com.tibco.cep.studio.dashboard.ui.statemachinecomponent.DashboardStateMachineComponentPlugin;
import com.tibco.cep.studio.dashboard.ui.statemachinecomponent.forms.ContentDataSettingsForm;
import com.tibco.cep.studio.dashboard.ui.statemachinecomponent.forms.IndicatorDataSettingsForm;
import com.tibco.cep.studio.dashboard.ui.statemachinecomponent.wizard.AbstractTemplateSettingsWizardPage;
import com.tibco.cep.studio.dashboard.ui.statemachinecomponent.wizard.AbstractTemplateSettingsWizardPage.TYPE;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class SettingsWizard extends Wizard {

	protected TYPE type;

	protected String lowerCaseType;

	protected String capitalizedCaseType;

	protected LocalStateVisualization stateVisualization;

	protected LocalStateMachineComponent tempLocalStateMachineComponent;

	protected LocalStateSeriesConfig tempSeriesConfig;

	protected NewSettingsWizardPage page;

	public SettingsWizard(TYPE type, LocalStateVisualization stateVisualization) throws Exception {
		this.type = type;
		this.lowerCaseType = this.type.toString().toLowerCase();
		StringBuilder sb = new StringBuilder(this.lowerCaseType);
		sb.replace(0, 1, String.valueOf(Character.toUpperCase(this.lowerCaseType.charAt(0))));
		this.capitalizedCaseType = sb.toString();
		String pageTitle = capitalizedCaseType+" Settings";
		page = new NewSettingsWizardPage(pageTitle+" for ['"+((State)stateVisualization.getElement(LocalStateVisualization.ELEMENT_KEY_STATE).getEObject()).getName()+"']");
		if (type.compareTo(TYPE.CONTENT) == 0) {
			page.setImageDescriptor(DashboardStateMachineComponentPlugin.getInstance().getImageRegistry().getDescriptor("contentdatasettings_wiz.png"));
		}
		else {
			page.setImageDescriptor(DashboardStateMachineComponentPlugin.getInstance().getImageRegistry().getDescriptor("indicatordatasettings_wiz.png"));
		}
		addPage(page);
		setWindowTitle("New "+pageTitle+" Wizard");
		setHelpAvailable(false);
		setNeedsProgressMonitor(false);
		setVisualization(stateVisualization);
	}

	@Override
	public void createPageControls(Composite pageContainer) {
		super.createPageControls(pageContainer);
		page.setSeriesConfig(tempSeriesConfig);
	}

	private void setVisualization(LocalStateVisualization stateVisualization) throws Exception {
		validateVisualization(stateVisualization);
		this.stateVisualization = stateVisualization;
		LocalDataSource defaultDataSource = StateMachineComponentHelper.getDefaultDataSource(stateVisualization);
		if (defaultDataSource == null) {
			if (stateVisualization.getRoot().getChildren(BEViewsElementNames.METRIC).isEmpty() == true) {
				page.setMessage("No metric found...", SettingsWizardPage.ERROR);
			} else {
				page.setMessage("No datasource found...", SettingsWizardPage.ERROR);
			}
		} else {
			//create a temp state model component, we need this so that the forms can get access to LocalECoreFactory
			tempLocalStateMachineComponent = new LocalStateMachineComponent(stateVisualization.getRoot(),"TempStateMachineComponent");
			//add a clone of the incoming state visualization, we do this to prevent the editor from getting dirty
			LocalStateVisualization tempStateVisualization = (LocalStateVisualization) stateVisualization.clone();
			tempLocalStateMachineComponent.addElement(LocalStateMachineComponent.ELEMENT_KEY_STATE_VISUALIZATION, tempStateVisualization);
			//add a template indicator series config
			tempSeriesConfig = createSeriesConfig(tempStateVisualization, defaultDataSource);
		}
	}

	protected void validateVisualization(LocalStateVisualization stateVisualization) throws Exception {
		if (type == TYPE.INDICATOR) {
			if (StateMachineComponentHelper.getIndicatorSeriesConfig(stateVisualization) != null){
				throw new IllegalArgumentException(stateVisualization+" already has indicator settings");
			}
		}
		else if (StateMachineComponentHelper.getContentSeriesConfig(stateVisualization) != null){
			throw new IllegalArgumentException(stateVisualization+" already has content settings");
		}
	}

	protected LocalStateSeriesConfig createSeriesConfig(LocalStateVisualization stateVisualization, LocalDataSource dataSource) throws Exception{
		if (type == TYPE.INDICATOR){
			return StateMachineComponentHelper.addTemplateIndicatorSeriesConfig(stateVisualization, dataSource);
		}
		else {
			return StateMachineComponentHelper.addTemplateContentSeriesConfig(stateVisualization, dataSource);
		}
	}

	@Override
	public boolean performCancel() {
		try {
			tempLocalStateMachineComponent.getRoot().removeElement(tempLocalStateMachineComponent,true);
		} catch (Exception e) {
			String message = "could not clean up "+lowerCaseType+" settings wizard operations on " + stateVisualization.toShortString();
			IStatus status = new Status(IStatus.WARNING, DashboardStateMachineComponentPlugin.PLUGIN_ID, message, e);
			DashboardStateMachineComponentPlugin.getInstance().getLog().log(status);
			MessageDialog.openError(Display.getCurrent().getActiveShell(), "Indicator Settings Wizard", "could not clean up "+lowerCaseType+" settings wizard operations due to " + e.getMessage());
			return true;
		}
		return true;
	}

	@Override
	public boolean performFinish() {
		try {
			stateVisualization.addElement(LocalStateVisualization.ELEMENT_KEY_STATE_SERIESCONFIG, (LocalElement) tempSeriesConfig.clone());
			StateMachineComponentHelper.syncSeriesConfigNames(stateVisualization);
		} catch (Exception e) {
			String message = "could not create "+lowerCaseType+"  settings in " + stateVisualization.toShortString();
			IStatus status = new Status(IStatus.WARNING, DashboardStateMachineComponentPlugin.PLUGIN_ID, message, e);
			DashboardStateMachineComponentPlugin.getInstance().getLog().log(status);
			MessageDialog.openError(Display.getCurrent().getActiveShell(), "Indicator Settings Wizard", message);
		} finally {
			try {
				tempLocalStateMachineComponent.getRoot().removeElement(tempLocalStateMachineComponent,true);
			} catch (Exception e) {
				String message = "could not clean up "+lowerCaseType+" settings wizard operations on " + stateVisualization.toShortString();
				IStatus status = new Status(IStatus.WARNING, DashboardStateMachineComponentPlugin.PLUGIN_ID, message, e);
				DashboardStateMachineComponentPlugin.getInstance().getLog().log(status);
			}
		}
		return true;
	}

	class SettingsWizardPage extends WizardPage {

		BaseForm baseForm;

		SettingsWizardPage(String title) {
			super("SettingsPage", title, null);
		}

		@Override
		public void createControl(Composite parent) {
			if (type == TYPE.INDICATOR) {
				IndicatorDataSettingsForm indicatorDataSettingsForm = new IndicatorDataSettingsForm(null, parent, SWT.VERTICAL);
				indicatorDataSettingsForm.setOtherDataSettingsVisible(false);
				baseForm = indicatorDataSettingsForm;
			}
			else {
				ContentDataSettingsForm contentDataSettingsForm = new ContentDataSettingsForm(null, parent, SWT.VERTICAL);
				contentDataSettingsForm.setOtherDataSettingsVisible(false);
				baseForm = contentDataSettingsForm;
			}
			baseForm.init();
			setControl(baseForm.getControl());

			if (tempSeriesConfig == null) {
				baseForm.disableAll();
			} else {
				try {
					baseForm.setInput(tempSeriesConfig);
				} catch (Exception e) {
					String message = "could not initialize "+lowerCaseType+" settings wizard on " + stateVisualization.toShortString();
					IStatus status = new Status(IStatus.ERROR, DashboardStateMachineComponentPlugin.PLUGIN_ID, message, e);
					DashboardStateMachineComponentPlugin.getInstance().getLog().log(status);
					setMessage(message, ERROR);
				}
			}
		}

		@Override
		public boolean isPageComplete() {
			if (getMessageType() == ERROR && getMessage() != null) {
				return false;
			}
			try {
				boolean valid = tempSeriesConfig.isValid();
				if (valid == false) {
					setErrorMessage(tempSeriesConfig.getValidationMessage().getMessage());
					return false;
				}
				return true;
			} catch (Exception e) {
				String message = "could not validate "+lowerCaseType+" settings on " + stateVisualization.toShortString();
				IStatus status = new Status(IStatus.WARNING, DashboardStateMachineComponentPlugin.PLUGIN_ID, message, e);
				DashboardStateMachineComponentPlugin.getInstance().getLog().log(status);
				setMessage(message, WARNING);
				return true;
			}
		}
	}

	class NewSettingsWizardPage extends AbstractTemplateSettingsWizardPage {

		NewSettingsWizardPage(String title) {
			super(type, "SettingsPage", title, null);
		}

		@Override
		protected BaseForm createForm(Composite parent) {
			if (type == TYPE.INDICATOR) {
				IndicatorDataSettingsForm indicatorDataSettingsForm = new IndicatorDataSettingsForm(null, parent, SWT.VERTICAL);
				indicatorDataSettingsForm.setOtherDataSettingsVisible(false);
				return indicatorDataSettingsForm;
			}
			else {
				ContentDataSettingsForm contentDataSettingsForm = new ContentDataSettingsForm(null, parent, SWT.VERTICAL);
				contentDataSettingsForm.setOtherDataSettingsVisible(false);
				return contentDataSettingsForm;
			}
		}

	}
}