package com.tibco.cep.studio.dashboard.ui.editors.views.page;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.tibco.cep.studio.dashboard.core.enums.InternalStatusEnum;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalPanel;
import com.tibco.cep.studio.dashboard.core.listeners.IMessageProvider;
import com.tibco.cep.studio.dashboard.core.listeners.ISynElementChangeListener;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalECoreFactory;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.DashboardUIPlugin;
import com.tibco.cep.studio.dashboard.ui.forms.AbstractSelectionListener;
import com.tibco.cep.studio.dashboard.ui.forms.ExceptionHandler;
import com.tibco.cep.studio.dashboard.ui.widgets.MemberComponentsEditor;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class ComponentSelectionDialog extends TitleAreaDialog {

	public static enum SELECTION_TYPE {

		ALL_COMPONENTS("Component Selector", "Component Selector", "Select Components"),

		CHART_OR_TEXT_COMPONENTS("Chart Selector", "Chart Selector", "Select Charts"),

		NON_CHART_OR_TEXT_COMPONENTS("Component Selector", "Component Selector", "Select Components");

		private String title;

		private String dialogTitle;

		private String defaultPrompt;

		private SELECTION_TYPE(String title, String dialogTitle, String defaultPrompt) {
			this.title = title;
			this.dialogTitle = dialogTitle;
			this.defaultPrompt = defaultPrompt;
		}

	}

	protected LocalECoreFactory localECoreFactory;

	protected List<LocalElement> allCharts;

	protected List<LocalElement> allNonCharts;

	protected List<LocalElement> existingComponents;

	private boolean warnAllSelection;

	private Button btn_showCharts;

	private Button btn_showNonCharts;

	protected MemberComponentsEditor componentsEditor;

	private LocalPanel tempPanel;

	private ISynElementChangeListener tempPanelListener;

	protected ExceptionHandler exHandler;

	private SELECTION_TYPE selectionType;

	public ComponentSelectionDialog(ExceptionHandler exHandler, Shell parentShell, LocalECoreFactory localECoreFactory, List<LocalElement> existingComponents, boolean warnAllSelection, SELECTION_TYPE selectionType)
			throws Exception {
		super(parentShell);
		setShellStyle(getShellStyle() | SWT.RESIZE);
		this.exHandler = exHandler;
		this.localECoreFactory = localECoreFactory;
		this.existingComponents = new LinkedList<LocalElement>();
		if (existingComponents != null) {
			this.existingComponents.addAll(existingComponents);
		}
		this.warnAllSelection = warnAllSelection;
		this.selectionType = selectionType;
		switch (this.selectionType) {
			case ALL_COMPONENTS:
				if (this.existingComponents != null && this.existingComponents.size() > 0) {
					throw new IllegalArgumentException("cannot specify existing components with " + SELECTION_TYPE.ALL_COMPONENTS);
				}
				allCharts = this.localECoreFactory.getChildren(BEViewsElementNames.TEXT_OR_CHART_COMPONENT);
				allNonCharts = this.localECoreFactory.getChildren(BEViewsElementNames.OTHER_COMPONENT);
				break;
			case CHART_OR_TEXT_COMPONENTS:
				allCharts = this.localECoreFactory.getChildren(BEViewsElementNames.TEXT_OR_CHART_COMPONENT);
				allCharts.removeAll(this.existingComponents);
				break;
			case NON_CHART_OR_TEXT_COMPONENTS:
				allNonCharts = this.localECoreFactory.getChildren(BEViewsElementNames.OTHER_COMPONENT);
				allNonCharts.removeAll(this.existingComponents);
				break;
		}
		tempPanelListener = new TempPanelComponentChildrenListener();
		if (this.selectionType.compareTo(SELECTION_TYPE.CHART_OR_TEXT_COMPONENTS) == 0) {
			setTitleImage(DashboardUIPlugin.getInstance().getImageRegistry().get("chart_selector_wizard.png"));
		}
		else {
			setTitleImage(DashboardUIPlugin.getInstance().getImageRegistry().get("component_selector_wizard.png"));
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

		createComponentSelectionControl(composite);

		// set layout data on the composite
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));

		dialogAreaComposite.setLayoutData(new GridData(GridData.FILL_BOTH));

		return dialogAreaComposite;
	}



	protected void createComponentSelectionControl(Composite composite) {
		setTitle(selectionType.dialogTitle);
		if (selectionType.compareTo(SELECTION_TYPE.ALL_COMPONENTS) == 0) {
			// show all charts button
			btn_showCharts = new Button(composite, SWT.RADIO);
			btn_showCharts.setText("Select Charts");
			btn_showCharts.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, true, false));

			// show non charts button
			btn_showNonCharts = new Button(composite, SWT.RADIO);
			btn_showNonCharts.setText("Select Non Charts");
			btn_showNonCharts.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, true, false));

			// create a switching composite
			final Composite switchingComposite = new Composite(composite, SWT.NONE);
			final StackLayout switchingLayout = new StackLayout();
			switchingComposite.setLayout(switchingLayout);

			// create all charts viewer
			final MemberComponentsEditor chartsViewer = new MemberComponentsEditor("Chart ", BEViewsElementNames.COMPONENT, null, switchingComposite, SWT.VERTICAL | SWT.MULTI, true);
			chartsViewer.setEnumeration(allCharts);
			final LocalPanel tempPanelForChartComponents = new LocalPanel(localECoreFactory, "TempPanelForChartComponents");
			tempPanelForChartComponents.subscribe(tempPanelListener, BEViewsElementNames.getComponentTypes());
			chartsViewer.setLocalElement(tempPanelForChartComponents);

			// create non charts viewer
			final MemberComponentsEditor nonChartsViewer = new MemberComponentsEditor(null, BEViewsElementNames.COMPONENT, null, switchingComposite, SWT.VERTICAL | SWT.SINGLE, true);
			nonChartsViewer.setEnumeration(allNonCharts);
			final LocalPanel tempPanelForNonChartComponents = new LocalPanel(localECoreFactory, "TempPanelForNonChartComponents");
			tempPanelForNonChartComponents.subscribe(tempPanelListener, BEViewsElementNames.getComponentTypes());
			nonChartsViewer.setLocalElement(tempPanelForNonChartComponents);

			// add selection listener to show charts button
			btn_showCharts.addSelectionListener(new AbstractSelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					if (btn_showCharts.getSelection() == true && switchingLayout.topControl != chartsViewer.getControl()) {
						switchingLayout.topControl = chartsViewer.getControl();
						switchingComposite.layout(true);
						componentsEditor = chartsViewer;
						tempPanel = tempPanelForChartComponents;
						validate();
					}

				}

			});

			// add selection listener to show non charts button
			btn_showNonCharts.addSelectionListener(new AbstractSelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					if (btn_showNonCharts.getSelection() == true && switchingLayout.topControl != nonChartsViewer.getControl()) {
						switchingLayout.topControl = nonChartsViewer.getControl();
						switchingComposite.layout(true);
						componentsEditor = nonChartsViewer;
						tempPanel = tempPanelForNonChartComponents;
						validate();
					}

				}

			});

			// set defaults to show charts
			btn_showCharts.setSelection(true);
			switchingLayout.topControl = chartsViewer.getControl();
			componentsEditor = chartsViewer;
			tempPanel = tempPanelForChartComponents;

			GridData switchingCompositeLayoutData = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
			switchingCompositeLayoutData.heightHint = 350;
			switchingComposite.setLayoutData(switchingCompositeLayoutData);
		}

		else if (selectionType.compareTo(SELECTION_TYPE.CHART_OR_TEXT_COMPONENTS) == 0) {
			// create all charts viewer
			componentsEditor = new MemberComponentsEditor("Chart ", BEViewsElementNames.COMPONENT, null, composite, SWT.VERTICAL | SWT.MULTI, true);
			componentsEditor.setEnumeration(allCharts);
			tempPanel = new LocalPanel(localECoreFactory, "TempPanelForChartComponents");
			tempPanel.subscribe(tempPanelListener, BEViewsElementNames.getComponentTypes());
			componentsEditor.setLocalElement(tempPanel);

			GridData chartsViewerLayoutData = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
			chartsViewerLayoutData.heightHint = 350;
			componentsEditor.getControl().setLayoutData(chartsViewerLayoutData);
		}

		else if (selectionType.compareTo(SELECTION_TYPE.NON_CHART_OR_TEXT_COMPONENTS) == 0) {
			// create non charts viewer
			componentsEditor = new MemberComponentsEditor(null, BEViewsElementNames.COMPONENT, null, composite, SWT.VERTICAL | SWT.SINGLE, true);
			componentsEditor.setEnumeration(allNonCharts);
			tempPanel = new LocalPanel(localECoreFactory, "TempPanelForChartComponents");
			tempPanel.subscribe(tempPanelListener, BEViewsElementNames.getComponentTypes());
			componentsEditor.setLocalElement(tempPanel);

			GridData chartsViewerLayoutData = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
			chartsViewerLayoutData.heightHint = 350;
			componentsEditor.getControl().setLayoutData(chartsViewerLayoutData);
		}

		validate();
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(selectionType.title);
		if (this.selectionType.compareTo(SELECTION_TYPE.CHART_OR_TEXT_COMPONENTS) == 0) {
			newShell.setImage(DashboardUIPlugin.getInstance().getImageRegistry().get("chart_selector_16x16.png"));
		}
		else {
			newShell.setImage(DashboardUIPlugin.getInstance().getImageRegistry().get("component_selector_16x16.png"));
		}
	}

	private void validate() {
		setMessage("");
		boolean okEnabled = false;
		if (tempPanel.getChildren(BEViewsElementNames.COMPONENT).isEmpty() == true) {
			setMessage(selectionType.defaultPrompt);
		} else {
			okEnabled = true;
			if (warnAllSelection == true) {
				if (selectionType.compareTo(SELECTION_TYPE.CHART_OR_TEXT_COMPONENTS) == 0 || (selectionType.compareTo(SELECTION_TYPE.ALL_COMPONENTS) == 0 && btn_showCharts.getSelection() == true)) {
					List<LocalElement> noComponentLeft = new LinkedList<LocalElement>(allCharts);
					noComponentLeft.removeAll(tempPanel.getChildren(BEViewsElementNames.COMPONENT));
					if (noComponentLeft.isEmpty() == true) {
						setMessage("All available charts selected", IStatus.WARNING);
					}
				}
			}
		}
		if (getButton(OK) != null) {
			getButton(OK).setEnabled(okEnabled);
		}
	}

	@Override
	protected Button createButton(Composite parent, int id, String label, boolean defaultButton) {
		Button button = super.createButton(parent, id, label, defaultButton);
		if (id == OK) {
			button.setEnabled(false);
		}
		return button;
	}

	public List<LocalElement> getSelections() {
		return tempPanel.getChildren(BEViewsElementNames.COMPONENT);
	}

	public LocalElement getSelection() {
		List<LocalElement> selections = getSelections();
		if (false == selections.isEmpty()) {
			return (LocalElement) selections.get(0);
		}
		return null;
	}

//	private void elementSelectorSelectionChanged() {
//		selections = elementViewer.getSelectedElements();
//		validate();
//	}

	class TempPanelComponentChildrenListener implements ISynElementChangeListener {

		@Override
		public void propertyChanged(IMessageProvider provider, SynProperty property, Object oldValue, Object newValue) {
		}

		@Override
		public void elementAdded(IMessageProvider parent, IMessageProvider newElement) {
			validate();
		}

		@Override
		public void elementRemoved(IMessageProvider parent, IMessageProvider removedElement) {
			validate();
		}

		@Override
		public void elementChanged(IMessageProvider parent, IMessageProvider changedElement) {
		}

		@Override
		public void elementStatusChanged(IMessageProvider parent, InternalStatusEnum status) {
		}

		@Override
		public String getName() {
			return "TempPanelComponentChildrenListener";
		}

	}

}