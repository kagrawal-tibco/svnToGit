package com.tibco.cep.studio.dashboard.ui.wizards.page.dashboard;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;

import com.tibco.cep.studio.dashboard.core.model.impl.LocalECoreFactory;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.DashboardUIPlugin;
import com.tibco.cep.studio.dashboard.ui.wizards.page.PageTemplateWizardPage;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class StandardDashboardPageWithStateMachineCompWizardPage extends PageTemplateWizardPage {

	private List<LocalElement> stateMachineComponents;
	private LocalElement stateMachineComponent;
	private int stateMachineComponentSpan;

	private Combo stateMachineCompDropDown;
	private Spinner stateMachineSpanSpinner;
	private ChartComponentsSelector chartComponentsSelector;

	private boolean userWantsEmptyPage;

	protected StandardDashboardPageWithStateMachineCompWizardPage(String pageName) {
		super("StandardDashboardPageWithStateMachineCompWizardPage", pageName, null);
		setDescription("Select component(s) to add to the page");
	}

	@Override
	public void createControl(Composite parent) {
		Composite pageComposite = new Composite(parent, SWT.NONE);
		pageComposite.setLayout(new GridLayout(2, false));
		pageComposite.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_FILL | GridData.HORIZONTAL_ALIGN_FILL));

		// state model component
		Label stateMachineCompLbl = new Label(pageComposite, SWT.NONE);
		stateMachineCompLbl.setText("State Model Component :");
		stateMachineCompLbl.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));

		stateMachineCompDropDown = new Combo(pageComposite, SWT.READ_ONLY);

		stateMachineCompDropDown.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		stateMachineCompDropDown.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);

			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				int selectionIndex = stateMachineCompDropDown.getSelectionIndex();
				stateMachineComponent = getStateMachineComponent(selectionIndex);
			}

		});

		// state model component span
		Label stateMachineSpanLbl = new Label(pageComposite, SWT.NONE);
		stateMachineSpanLbl.setText("State Model Component Height :");
		stateMachineSpanLbl.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));

		stateMachineSpanSpinner = new Spinner(pageComposite, SWT.BORDER);
		stateMachineSpanSpinner.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
		stateMachineSpanSpinner.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				stateMachineComponentSpan = stateMachineSpanSpinner.getSelection();
			}

		});

		Label separator = new Label(pageComposite, SWT.SEPARATOR | SWT.HORIZONTAL);
		separator.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		chartComponentsSelector = new ChartComponentsSelector(pageComposite,SWT.NONE);
		chartComponentsSelector.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

		setControl(pageComposite);
	}

	@Override
	public void populateControl(){
		try {
			stateMachineCompDropDown.setItems(getAvailableStateMachineComponents());
			stateMachineCompDropDown.select(0);
			stateMachineComponentSpan = 20;
			stateMachineSpanSpinner.setValues(stateMachineComponentSpan, 10, 80, 0, 1, 10);
			stateMachineComponent = getStateMachineComponent(0);
			chartComponentsSelector.setProject(project);
			chartComponentsSelector.populateControl();
		} catch (Exception e) {
			DashboardUIPlugin.getInstance().getLog().log(new Status(IStatus.ERROR,DashboardUIPlugin.PLUGIN_ID,"could not populate "+getName(),e));
			setErrorMessage("could not populate "+getName());
			setPageComplete(false);
		}
	}

	private String[] getAvailableStateMachineComponents() {
		try {
			stateMachineComponents = LocalECoreFactory.getInstance(project).getChildren(BEViewsElementNames.STATE_MACHINE_COMPONENT);
			if (stateMachineComponents.isEmpty() == true) {
				return new String[] { "None" };
			}
			String[] names = new String[stateMachineComponents.size()];
			for (int i = 0; i < names.length; i++) {
				names[i] = stateMachineComponents.get(i).getName();
			}
			return names;
		} catch (Exception e) {
			setErrorMessage("could not retrieve state model component list");
			return new String[] { "None" };
		}
	}

	private LocalElement getStateMachineComponent(int selectionIndex) {
		if (stateMachineComponents.isEmpty() == true) {
			return null;
		}
		return stateMachineComponents.get(selectionIndex);
	}

	public int getStateMachineComponentSpan() {
		return stateMachineComponentSpan;
	}

	public LocalElement getStateMachineComponent() {
		return stateMachineComponent;
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
	public void setSelectedComponents(List<LocalElement> selectedComponents) {
		//isolate state model component and chart components
		List<LocalElement> selectedStateMachineComponents = new LinkedList<LocalElement>();
		List<LocalElement> selectedChartComponents = new LinkedList<LocalElement>();
		for (LocalElement localElement : selectedComponents) {
			if (BEViewsElementNames.getChartOrTextComponentTypes().contains(localElement.getElementType()) == true) {
				selectedChartComponents.add(localElement);
			}
			else if (BEViewsElementNames.STATE_MACHINE_COMPONENT.equals(localElement.getElementType()) == true) {
				selectedStateMachineComponents.add(localElement);
			}
			else {
				//what do to ?
			}
		}
		if (selectedStateMachineComponents.isEmpty() == false) {
			int i = stateMachineComponents.indexOf(selectedStateMachineComponents.get(0));
			if (i != -1){
				stateMachineCompDropDown.select(i);
				stateMachineComponent = selectedStateMachineComponents.get(0);
			}
		}
		chartComponentsSelector.setSelection(selectedChartComponents);
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
}
