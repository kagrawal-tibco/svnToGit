package com.tibco.cep.studio.dashboard.ui.wizards.page.dashboard;

import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.DashboardUIPlugin;
import com.tibco.cep.studio.dashboard.ui.wizards.page.PageTemplateWizardPage;

public class StandardDashboardPageWizardPage extends PageTemplateWizardPage {

	private ChartComponentsSelector chartComponentsSelector;

	private boolean userWantsEmptyPage;

	protected StandardDashboardPageWizardPage(String pageName) {
		super("StandardDashboardPageWizardPage", pageName, null);
		setDescription("Select component(s) to add to the page");
	}

	@Override
	public void createControl(Composite parent) {
		Composite pageComposite = new Composite(parent, SWT.NONE);
		pageComposite.setLayout(new FillLayout());
		pageComposite.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_FILL | GridData.HORIZONTAL_ALIGN_FILL));
		chartComponentsSelector = new ChartComponentsSelector(pageComposite,SWT.NONE);
		setControl(pageComposite);
	}

	@Override
	public void populateControl(){
		try {
			chartComponentsSelector.setProject(project);
			chartComponentsSelector.populateControl();
		} catch (Exception e) {
			DashboardUIPlugin.getInstance().getLog().log(new Status(IStatus.ERROR,DashboardUIPlugin.PLUGIN_ID,"could not populate "+getName(),e));
			setErrorMessage("could not populate "+getName());
			setPageComplete(false);
		}
	}

	public List<LocalElement> getChartComponents() {
		if (chartComponentsSelector.getChartComponents().isEmpty() == true) {
			if (userWantsEmptyPage == true){
				return null;
			}
		}
		return chartComponentsSelector.getChartComponents();
	}

	@Override
	public boolean canFinish() {
		if (chartComponentsSelector.getChartComponents().isEmpty() == true) {
			Shell shell = getContainer().getShell();
			MessageDialog dlg = new MessageDialog(shell, shell.getText(), null, "Are you sure you want to create an empty dashboard page?", MessageDialog.QUESTION, new String[]{IDialogConstants.YES_LABEL, IDialogConstants.NO_LABEL}, 1);
			int returnCode = dlg.open();
			userWantsEmptyPage = (returnCode == MessageDialog.OK);
			return userWantsEmptyPage;
		}
		return super.canFinish();
	}

	@Override
	public void setSelectedComponents(List<LocalElement> selectedComponents) {
		chartComponentsSelector.setSelection(selectedComponents);
	}
}
