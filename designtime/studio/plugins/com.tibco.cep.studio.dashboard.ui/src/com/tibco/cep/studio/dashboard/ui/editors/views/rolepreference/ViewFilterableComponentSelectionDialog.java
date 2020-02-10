package com.tibco.cep.studio.dashboard.ui.editors.views.rolepreference;

import java.util.List;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalRolePreference;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalView;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalECoreFactory;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.util.StringUtil;
import com.tibco.cep.studio.dashboard.ui.editors.views.page.ComponentSelectionDialog;
import com.tibco.cep.studio.dashboard.ui.forms.AbstractSelectionListener;
import com.tibco.cep.studio.dashboard.ui.forms.ExceptionHandler;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class ViewFilterableComponentSelectionDialog extends ComponentSelectionDialog {

	private LocalView view;

	private List<LocalElement> allChartsInView;

	public ViewFilterableComponentSelectionDialog(ExceptionHandler exHandler, Shell parentShell, LocalRolePreference rolePreference, List<LocalElement> existingComponents, boolean warnAllSelection) throws Exception {
		super(exHandler, parentShell, (LocalECoreFactory) rolePreference.getRoot(), existingComponents, warnAllSelection, SELECTION_TYPE.CHART_OR_TEXT_COMPONENTS);
		view = (LocalView) rolePreference.getElement(BEViewsElementNames.VIEW);
		if (view != null) {
			allChartsInView = view.getComponents(BEViewsElementNames.getChartOrTextComponentTypes());
			allChartsInView.removeAll(existingComponents);
		}
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		// create a composite with standard margins and spacing
		Composite dialogAreaComposite = new Composite(parent, SWT.NONE);

		GridLayout dialogAreaCompLayout = new GridLayout(1, true);
		dialogAreaCompLayout.marginHeight = 0;
		dialogAreaCompLayout.marginWidth = 0;
		dialogAreaCompLayout.verticalSpacing = 0;
		dialogAreaCompLayout.horizontalSpacing = 0;
		dialogAreaComposite.setLayout(dialogAreaCompLayout);

		// Build the separator line
		Label titleBarSeparator = new Label(dialogAreaComposite, SWT.HORIZONTAL | SWT.SEPARATOR);
		titleBarSeparator.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));

		// the main composite with dialog specific width/heights/spacings
		Composite composite = new Composite(dialogAreaComposite, SWT.NONE);
		// create the layout with standard margins and spacing
		GridLayout layout = new GridLayout(2, true);
		layout.marginHeight = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_MARGIN);
		layout.marginWidth = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
		layout.verticalSpacing = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING);
		layout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
		composite.setLayout(layout);

		//add check box for limiting to charts in view
		if (view != null) {
			final Button btn_limitToViewCharts = new Button(composite,SWT.CHECK);
			btn_limitToViewCharts.setText("Limit to charts used in the selected view '"+(StringUtil.isEmpty(view.getDisplayName()) == true ? view.getName() : view.getDisplayName())+"'");
			btn_limitToViewCharts.setToolTipText("Limits the selectable charts to those found in the view");
			btn_limitToViewCharts.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
			btn_limitToViewCharts.addSelectionListener(new AbstractSelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					if (btn_limitToViewCharts.getSelection() == true) {
						componentsEditor.setEnumeration(allChartsInView);
					}
					else {
						componentsEditor.setEnumeration(allCharts);
					}
				}

			});
		}

		createComponentSelectionControl(composite);

		// set layout data on the composite
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));

		dialogAreaComposite.setLayoutData(new GridData(GridData.FILL_BOTH));

		return dialogAreaComposite;
	}

}
