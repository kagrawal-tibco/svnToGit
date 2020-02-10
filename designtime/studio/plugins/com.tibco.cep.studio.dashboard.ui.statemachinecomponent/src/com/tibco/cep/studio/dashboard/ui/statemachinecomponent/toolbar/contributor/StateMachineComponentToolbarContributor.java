package com.tibco.cep.studio.dashboard.ui.statemachinecomponent.toolbar.contributor;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;

import com.tibco.cep.studio.dashboard.ui.actiondelegate.IComponentContributor;
import com.tibco.cep.studio.dashboard.ui.statemachinecomponent.DashboardStateMachineComponentPlugin;
import com.tibco.cep.studio.dashboard.ui.statemachinecomponent.wizard.NewStateMachineComponentWizard;

public class StateMachineComponentToolbarContributor implements IComponentContributor, IExecutableExtension {

	private static final String PRIORITY_ATTRIBUTE = "priority";

	StateMachineComponentWizardAction smcWizardAction;

	private int intPriority = -1;

	private IStructuredSelection selection;

	public StateMachineComponentToolbarContributor() {
		smcWizardAction = new StateMachineComponentWizardAction();
	}

	@Override
	public void selectionChanged(IStructuredSelection selection) {
		this.selection = selection;
	}

	@Override
	public Action getAction() {
		return smcWizardAction;
	}

	class StateMachineComponentWizardAction extends Action {

		public StateMachineComponentWizardAction() {
			super("State Model Component", AS_PUSH_BUTTON);
			setImageDescriptor(DashboardStateMachineComponentPlugin.getInstance().getImageRegistry().getDescriptor("statemachinecomponent_new_16x16.png"));
			setDisabledImageDescriptor(DashboardStateMachineComponentPlugin.getInstance().getImageRegistry().getDescriptor("statemachinecomponent_new_disabled_16x16.png"));
			setToolTipText("New State Model Component");
		}

		public void run() {
			NewStateMachineComponentWizard wizard = new NewStateMachineComponentWizard();
			wizard.init(PlatformUI.getWorkbench(), selection);
			WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), wizard);
			dialog.open();
		}
	}

	@Override
	public void setInitializationData(IConfigurationElement config, String propertyName, Object data) throws CoreException {
		String strPriority = config.getAttribute(PRIORITY_ATTRIBUTE);
		try {
			intPriority = Integer.parseInt(strPriority);
		} catch (NumberFormatException ex) {
			System.err.println("StateMachineComponentToolbarContributor: unable to convert '" + PRIORITY_ATTRIBUTE + "' attribute '" + strPriority + "' to an integer.");
			throw new WorkbenchException("StateMachineComponentToolbarContributor: unable to convert '" + PRIORITY_ATTRIBUTE + "' attribute '" + strPriority + "' to an integer.", ex);
		}
	}

	@Override
	public int getPriority() {
		return intPriority;
	}
}
