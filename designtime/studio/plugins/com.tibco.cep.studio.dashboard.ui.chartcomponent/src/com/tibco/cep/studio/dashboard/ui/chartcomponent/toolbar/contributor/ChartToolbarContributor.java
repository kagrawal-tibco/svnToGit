package com.tibco.cep.studio.dashboard.ui.chartcomponent.toolbar.contributor;


import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;

import com.tibco.cep.studio.dashboard.ui.actiondelegate.IComponentContributor;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.DashboardChartPlugin;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.wizard.ChartWizard;

public class ChartToolbarContributor implements IComponentContributor, IExecutableExtension {

	private static final String PRIORITY_ATTRIBUTE = "priority";

	ChartComponentWizardAction chartWizardAction;

	private int intPriority = -1;

	private IStructuredSelection selection;

	public ChartToolbarContributor() {
		chartWizardAction = new ChartComponentWizardAction();
	}

	@Override
	public void selectionChanged(IStructuredSelection selection) {
		this.selection = selection;
	}

	@Override
	public Action getAction() {
		return chartWizardAction;
	}

	class ChartComponentWizardAction extends Action {

		public ChartComponentWizardAction() {
			super("Chart Component", AS_PUSH_BUTTON);
			setImageDescriptor(DashboardChartPlugin.getDefault().getImageRegistry().getDescriptor("chartcomponent_new_16x16.png"));
			setDisabledImageDescriptor(DashboardChartPlugin.getDefault().getImageRegistry().getDescriptor("chartcomponent_new_disabled_16x16.png"));
			setToolTipText("New Chart Component");
		}

		public void run() {
			ChartWizard wizard = new ChartWizard();
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
			System.err.println("ChartToolbarContributor: unable to convert '" + PRIORITY_ATTRIBUTE + "' attribute '" + strPriority + "' to an integer.");
			throw new WorkbenchException("ChartToolbarContributor: unable to convert '" + PRIORITY_ATTRIBUTE + "' attribute '" + strPriority + "' to an integer.", ex);
		}
	}

	@Override
	public int getPriority() {
		return intPriority;
	}
}
