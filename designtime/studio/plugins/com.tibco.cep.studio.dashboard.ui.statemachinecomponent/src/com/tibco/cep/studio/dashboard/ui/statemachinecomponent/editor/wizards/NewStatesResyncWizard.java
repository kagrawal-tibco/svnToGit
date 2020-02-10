package com.tibco.cep.studio.dashboard.ui.statemachinecomponent.editor.wizards;

import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import com.tibco.cep.studio.dashboard.core.insight.model.LocalExternalReference;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalDataSource;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalStateMachineComponent;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalStateVisualization;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.helpers.StateMachineComponentHelper;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalECoreFactory;
import com.tibco.cep.studio.dashboard.ui.statemachinecomponent.DashboardStateMachineComponentPlugin;
import com.tibco.cep.studio.dashboard.ui.statemachinecomponent.wizard.AbstractTemplateSettingsWizardPage;
import com.tibco.cep.studio.dashboard.ui.statemachinecomponent.wizard.ContentTemplateAlertSettingsWizardPage;
import com.tibco.cep.studio.dashboard.ui.statemachinecomponent.wizard.ContentTemplateDataSettingsWizardPage;
import com.tibco.cep.studio.dashboard.ui.statemachinecomponent.wizard.IndicatorTemplateAlertSettingsWizardPage;
import com.tibco.cep.studio.dashboard.ui.statemachinecomponent.wizard.IndicatorTemplateDataSettingsWizardPage;
import com.tibco.cep.studio.dashboard.ui.statemachinecomponent.wizard.StateMachineComponentTemplatesController;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class NewStatesResyncWizard extends Wizard {

	private LocalStateMachineComponent component;

	private LocalDataSource defaultDataSource;

	private StateMachineComponentTemplatesController templatesController;

	private List<LocalExternalReference> stateRefs;

	public NewStatesResyncWizard(LocalStateMachineComponent component,List<LocalExternalReference> stateRefs) {
		super();
		this.component = component;
		this.stateRefs = stateRefs;
		setHelpAvailable(false);
		setNeedsProgressMonitor(false);
		setWindowTitle("New State Resync Wizard");
		addPage(new IndicatorTemplateDataSettingsWizardPage());
		addPage(new ContentTemplateDataSettingsWizardPage());
		addPage(new IndicatorTemplateAlertSettingsWizardPage());
		addPage(new ContentTemplateAlertSettingsWizardPage());
	}

	@Override
	public void createPageControls(Composite pageContainer) {
		super.createPageControls(pageContainer);
		IWizardPage[] pages = getPages();
		WizardPage firstPage = (WizardPage) pages[0];
		//find the default data source
		try {
			defaultDataSource = StateMachineComponentHelper.getDefaultDataSource(component);
			if (defaultDataSource == null){
				if (component.getRoot().getChildren(BEViewsElementNames.METRIC).isEmpty() == true){
					firstPage.setErrorMessage("No metrics found...");
					firstPage.setPageComplete(false);
				}
				else {
					firstPage.setErrorMessage("No datasources found...");
					firstPage.setPageComplete(false);
				}
			}
			else {
				//create the templates controller
				try {
					String folder = component.getFolder();
					templatesController = new StateMachineComponentTemplatesController(((LocalECoreFactory)component.getRoot()).getProject(),folder,folder,defaultDataSource);
					templatesController.createTemplateComponent();
				} catch (Exception e) {
					IStatus status = new Status(IStatus.ERROR,DashboardStateMachineComponentPlugin.PLUGIN_ID,"could not create settings templates for "+stateRefs.size()+" new states in "+component,e);
					DashboardStateMachineComponentPlugin.getInstance().getLog().log(status);
				}
				for (IWizardPage page : pages) {
					if (page instanceof AbstractTemplateSettingsWizardPage){
						try {
							templatesController.prepPage((AbstractTemplateSettingsWizardPage) page);
						} catch (Exception e) {
							IStatus status = new Status(IStatus.WARNING,DashboardStateMachineComponentPlugin.PLUGIN_ID,"could not prep '"+page.getName()+"' for "+component,e);
							DashboardStateMachineComponentPlugin.getInstance().getLog().log(status);
						}
					}
				}
			}
		} catch (Exception e) {
			IStatus status = new Status(IStatus.ERROR,DashboardStateMachineComponentPlugin.PLUGIN_ID,"could not load datasource(s) for creating settings templates for "+stateRefs.size()+" new states in "+component,e);
			DashboardStateMachineComponentPlugin.getInstance().getLog().log(status);
			firstPage.setErrorMessage("could not load datasource(s)...");
			firstPage.setPageComplete(false);
		}
	}

	@Override
	public boolean performFinish() {
		if (templatesController == null || templatesController.getTemplateStateVisualization() == null){
			MessageDialog.openError(Display.getCurrent().getActiveShell(), getWindowTitle(), "could not add visualization for "+stateRefs.size()+" new states");
			return false;
		}
		else {
			try {
				for (LocalExternalReference stateRef : stateRefs) {
					LocalStateVisualization stateViz = (LocalStateVisualization) templatesController.getTemplateStateVisualization().clone();
					stateViz.setElement(LocalStateVisualization.ELEMENT_KEY_STATE, stateRef);
					component.addElement(LocalStateMachineComponent.ELEMENT_KEY_STATE_VISUALIZATION, stateViz);
					StateMachineComponentHelper.syncAllNames(stateViz);
				}
			} catch (Exception e) {
				IStatus status = new Status(IStatus.WARNING,DashboardStateMachineComponentPlugin.PLUGIN_ID,"could not add visualization for "+stateRefs.size()+" new states in "+component,e);
				DashboardStateMachineComponentPlugin.getInstance().getLog().log(status);
				MessageDialog.openError(Display.getCurrent().getActiveShell(), getWindowTitle(), "could not add visualization for "+stateRefs.size()+" new states");
				return false;
			}
		}
		return true;
	}

}