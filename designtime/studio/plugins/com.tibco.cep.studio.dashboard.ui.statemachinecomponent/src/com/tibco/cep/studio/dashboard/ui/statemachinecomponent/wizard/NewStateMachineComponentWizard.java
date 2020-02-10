package com.tibco.cep.studio.dashboard.ui.statemachinecomponent.wizard;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.wizard.IWizardPage;

import com.tibco.cep.studio.dashboard.core.insight.model.LocalConfig;
import com.tibco.cep.studio.dashboard.core.insight.model.LocalExternalReference;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalStateMachineComponent;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.helpers.StateMachineComponentHelper;
import com.tibco.cep.studio.dashboard.ui.statemachinecomponent.DashboardStateMachineComponentPlugin;
import com.tibco.cep.studio.dashboard.ui.utils.DashboardResourceUtils;
import com.tibco.cep.studio.dashboard.ui.wizards.BaseViewsElementWizard;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;
import com.tibco.cep.studio.ui.wizards.EntityFileCreationWizard;

public class NewStateMachineComponentWizard extends BaseViewsElementWizard {

	private StateMachineSelectionWizardPage stateMachineSelectionWizardPage;

	private StateMachineComponentTemplatesController templatesController;

	public NewStateMachineComponentWizard() {
		super(BEViewsElementNames.STATE_MACHINE_COMPONENT, "State Model Component", "New State Model Component Wizard", "StateMachineComponentPage", "New State Model Component", "Create a new State Model Component");
		setDefaultPageImageDescriptor(DashboardStateMachineComponentPlugin.getInstance().getImageRegistry().getDescriptor("statemachinecomponent_wizard.png"));
		setHelpAvailable(false);
		setNeedsProgressMonitor(false);
	}

	@Override
	public void addPages() {
		super.addPages();
		addPage(new IndicatorTemplateDataSettingsWizardPage());
		addPage(new ContentTemplateDataSettingsWizardPage());
		addPage(new IndicatorTemplateAlertSettingsWizardPage());
		addPage(new ContentTemplateAlertSettingsWizardPage());
	}

	@Override
	protected EntityFileCreationWizard createPage() {
		stateMachineSelectionWizardPage = new StateMachineSelectionWizardPage(pageName, _selection, elementType, elementTypeName);
		stateMachineSelectionWizardPage.setDescription(pageDesc);
		stateMachineSelectionWizardPage.setTitle(pageTitle);
		return stateMachineSelectionWizardPage;
	}

	@Override
	public IWizardPage getNextPage(IWizardPage page) {
		IWizardPage nextPage = super.getNextPage(page);
		if (nextPage instanceof AbstractTemplateSettingsWizardPage){
			try {
				getTemplatesController().prepPage((AbstractTemplateSettingsWizardPage) nextPage);
			} catch (Exception e) {
				IStatus status = new Status(IStatus.WARNING,DashboardStateMachineComponentPlugin.PLUGIN_ID,"could not prep '"+nextPage.getName()+"' for "+_selection,e);
				DashboardStateMachineComponentPlugin.getInstance().getLog().log(status);
			}
		}
		return nextPage;
	}

	@Override
	public boolean performFinish() {
		try {
			getTemplatesController();
			return super.performFinish();
		} finally {
			if (templatesController != null){
				try {
					getTemplatesController().deleteTemplateComponent();
				} catch (Exception ignore) {
				}
			}
		}
	}

	@Override
	public boolean performCancel() {
		if (templatesController != null){
			try {
				getTemplatesController().deleteTemplateComponent();
			} catch (Exception ignore) {
			}
		}
		return super.performCancel();
	}

	private StateMachineComponentTemplatesController getTemplatesController() {
		if (templatesController == null){
			String folder = DashboardResourceUtils.getFolder(stateMachineSelectionWizardPage.getModelFile());
			templatesController = new StateMachineComponentTemplatesController(stateMachineSelectionWizardPage.getProject(),folder,folder,stateMachineSelectionWizardPage.getDefaultDataSource());
			try {
				templatesController.createTemplateComponent();
			} catch (Exception e) {
				IStatus status = new Status(IStatus.WARNING,DashboardStateMachineComponentPlugin.PLUGIN_ID,"could not create template state visualization for "+_selection,e);
				DashboardStateMachineComponentPlugin.getInstance().getLog().log(status);
			}
		}
		return templatesController;
	}

	@Override
	protected void persistFirstPage(LocalConfig localConfig, String elementName, String elementDesc, String baseURI, IProgressMonitor monitor) throws Exception {
		LocalStateMachineComponent component = (LocalStateMachineComponent) localConfig;
		StateMachineComponentHelper.setStateMachine(component, new LocalExternalReference(stateMachineSelectionWizardPage.getStateMachine()),getTemplatesController().getTemplateStateVisualization());
		super.persistFirstPage(localConfig, elementName, elementDesc, baseURI, monitor);
	}
}