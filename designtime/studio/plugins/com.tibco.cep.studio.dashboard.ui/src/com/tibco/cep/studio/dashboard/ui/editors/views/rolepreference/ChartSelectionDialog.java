package com.tibco.cep.studio.dashboard.ui.editors.views.rolepreference;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.tibco.cep.studio.dashboard.core.insight.model.LocalConfig;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalRolePreference;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalView;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalECoreFactory;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.forms.AbstractSelectionListener;
import com.tibco.cep.studio.dashboard.ui.forms.ExceptionHandler;
import com.tibco.cep.studio.dashboard.ui.viewers.ElementSelector;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class ChartSelectionDialog extends TitleAreaDialog {

	private static final String TITLE = "Chart Selector";

	private static final String DIALOG_TITLE = "Chart Selector";

	private static final String DEFAULT_PROMPT = "Select charts";

	private LocalRolePreference localRolePreference;

	private LocalECoreFactory localECoreFactory;

	private List<LocalElement> allCharts;

	private List<LocalElement> existingCharts;

	private boolean multiSelect;

	private boolean warnAllChartSelection;

	private Button btn_showAllCharts;

	private Button btn_searchAllCharts;

	private Text txt_searchQuery;

	private Button btn_Search;

	private List<Action> actions;

	private ElementSelector viewer;

	private ExceptionHandler exHandler;

	private List<LocalElement> selections;

	public ChartSelectionDialog(ExceptionHandler exHandler, Shell parentShell, LocalRolePreference rolePreference, List<LocalElement> existingCharts, boolean multiSelect, boolean warnAllChartSelection) throws Exception {
		super(parentShell);
		setShellStyle(getShellStyle() | SWT.RESIZE);
		this.exHandler = exHandler;
		this.localRolePreference = rolePreference;
		this.localECoreFactory = (LocalECoreFactory) this.localRolePreference.getRoot();
		this.allCharts = localECoreFactory.getChildren(BEViewsElementNames.TEXT_OR_CHART_COMPONENT);
		this.existingCharts = existingCharts;
		this.multiSelect = multiSelect;
		this.warnAllChartSelection = warnAllChartSelection;
		this.selections = new LinkedList<LocalElement>();
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		// create a composite with standard margins and spacing
		Composite dialogAreaComposite = new Composite(parent, SWT.NONE);

		GridLayout dialogAreaCompLayout = new GridLayout(1,true);
		dialogAreaCompLayout.marginHeight = 0;
		dialogAreaCompLayout.marginWidth = 0;
		dialogAreaCompLayout.verticalSpacing = 0;
		dialogAreaCompLayout.horizontalSpacing = 0;
		dialogAreaComposite.setLayout(dialogAreaCompLayout);

		// Build the separator line
		Label titleBarSeparator = new Label(dialogAreaComposite, SWT.HORIZONTAL | SWT.SEPARATOR);
		titleBarSeparator.setLayoutData(new GridData(SWT.FILL,SWT.TOP,true,false));

		//the main composite with dialog specific width/heights/spacings
		Composite composite = new Composite(dialogAreaComposite, SWT.NONE);
		//create the layout with standard margins and spacing
		GridLayout layout = new GridLayout(1,true);
		layout.marginHeight = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_MARGIN);
		layout.marginWidth = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
		layout.verticalSpacing = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING);
		layout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
		composite.setLayout(layout);

		//show all charts button
		btn_showAllCharts = new Button(composite,SWT.RADIO);
		btn_showAllCharts.setText("Show All Charts");
		btn_showAllCharts.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, true, false));

		btn_searchAllCharts = new Button(composite,SWT.RADIO);
		btn_searchAllCharts.setText("Search All Charts");
		btn_searchAllCharts.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, true, false));

		//search form
		Composite searchFormCompsite = new Composite(composite, SWT.NONE);
		searchFormCompsite.setLayout(new GridLayout(2,false));

		//search query field
		txt_searchQuery = new Text(searchFormCompsite, SWT.SINGLE | SWT.BORDER);
		txt_searchQuery.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		//start search button
		btn_Search = new Button(searchFormCompsite, SWT.PUSH);
		btn_Search.setText("Search");

		GridData btn_layoutData = new GridData(SWT.FILL, SWT.FILL, false, false);
		btn_layoutData.widthHint = convertHorizontalDLUsToPixels(IDialogConstants.BUTTON_WIDTH);
		btn_Search.setLayoutData(btn_layoutData);

		//set layout data on search form group
		GridData searchFormGrpLayout = new GridData(SWT.FILL, SWT.FILL, true, false);
		searchFormGrpLayout.horizontalIndent = 15;
		searchFormCompsite.setLayoutData(searchFormGrpLayout);

		//search result form
		Group searchResultsGroup = new Group(composite, SWT.NONE);
		searchResultsGroup.setText("Results");
		searchResultsGroup.setLayout(new GridLayout(4,true));

		//create viewer before creating actions, since we borrow actions from the viewer
		viewer = new ElementSelector(searchResultsGroup, new LocalElement[0], multiSelect, false);
		//layout the viewer
		GridData viewLayoutData = new GridData(SWT.FILL, SWT.FILL, true, true, 4, 1);
		viewLayoutData.heightHint = 200;
		viewer.getTable().setLayoutData(viewLayoutData);

		//actions
		createActions();
		//hook up the actions to table
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		for (Action action : actions) {
			menuMgr.add(action);
		}
		viewer.getTable().setMenu(menuMgr.createContextMenu(viewer.getTable()));

		//set layout data on search form group
		searchResultsGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		//set layout data on the composite
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));

		dialogAreaComposite.setLayoutData(new GridData(GridData.FILL_BOTH));

		setTitle(DIALOG_TITLE);

		//hook up all listeners
		hookListeners();
		//set initial state
		setInitialState();

		return dialogAreaComposite;
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(TITLE);
	}

	private void createActions() {
		actions = new LinkedList<Action>();
		actions.add(viewer.getSelectAllAction());
		actions.add(viewer.getUnselectAllAction());
		actions.add(viewer.getInvertSelectionAction());
		actions.add(new SelectChartsFromView());
	}

	private void updateActions(){
		LocalElement[] input = (LocalElement[]) viewer.getInput();
		List<LocalElement> selectedElements = viewer.getSelectedElements();
		viewer.getSelectAllAction().setEnabled(selectedElements.size() != input.length);
		viewer.getUnselectAllAction().setEnabled(selectedElements.size() > 0);
		viewer.getInvertSelectionAction().setEnabled(selectedElements.size() > 0);
	}

	private void hookListeners() {
		btn_showAllCharts.addSelectionListener(new AbstractSelectionListener(){

			@Override
			public void widgetSelected(SelectionEvent e) {
				showAllChartsClicked();
			}

		});

		btn_searchAllCharts.addSelectionListener(new AbstractSelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				searchAllChartsClicked();
			}

		});

		btn_Search.addSelectionListener(new AbstractSelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				searchButtonClicked();
			}

		});

		viewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				selections = viewer.getSelectedElements();
				validate();
				updateActions();
			}

		});

		viewer.addDoubleClickListener(new IDoubleClickListener(){

			@Override
			public void doubleClick(DoubleClickEvent event) {
				validate();
				if (getSelection() != null) {
					okPressed();
				}
			}

		});

	}

	private void setInitialState() {
		//select show all charts
		btn_showAllCharts.setSelection(true);
		showAllChartsClicked();
	}

	private void validate() {
		setMessage("");
		boolean okEnabled = false;
		if (viewer.getSelectedElements().isEmpty() == true) {
			setMessage(DEFAULT_PROMPT);
		}
		else {
			okEnabled = true;
			if (warnAllChartSelection == true) {
				List<LocalElement> noChartsLeft = new LinkedList<LocalElement>(allCharts);
				noChartsLeft.removeAll(viewer.getSelectedElements());
				if (noChartsLeft.isEmpty() == true) {
					setMessage("All charts in the project selected", IStatus.WARNING);
				}
			}
		}
		if (getButton(OK) != null) {
			getButton(OK).setEnabled(okEnabled);
		}
	}

	private void showAllChartsClicked() {
		//disable search query
		txt_searchQuery.setEnabled(false);
		btn_Search.setEnabled(false);
		//show all charts in the table minus existing charts
		List<LocalElement> input = new LinkedList<LocalElement>(allCharts);
		input.removeAll(existingCharts);
		viewer.setInput(input.toArray());
		viewer.refresh();
		validate();
	}

	private void searchAllChartsClicked() {
		//enabled search query
		txt_searchQuery.setEnabled(true);
		btn_Search.setEnabled(true);
		//remove all entries from the viewer
		viewer.setInput(Collections.emptyList());
		viewer.refresh();
		validate();
	}

	private void searchButtonClicked() {
		String searchQuery = txt_searchQuery.getText().toLowerCase();
		try {
			List<LocalElement> children = localECoreFactory.getChildren(BEViewsElementNames.TEXT_OR_CHART_COMPONENT);
			List<LocalElement> results = new LinkedList<LocalElement>();
			for (LocalElement child : children) {
				if (child.getName().toLowerCase().indexOf(searchQuery) != -1 || child.getPropertyValue(LocalConfig.PROP_KEY_DISPLAY_NAME).toLowerCase().indexOf(searchQuery) != -1) {
					results.add(child);
				}
			}
			viewer.setInput(results.toArray());
			viewer.refresh();
			validate();
		} catch (Exception e) {
			setErrorMessage("could not search charts");
			exHandler.log(exHandler.createStatus(IStatus.ERROR, "could not search charts for '"+searchQuery+"'", e));
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
		return selections;
	}

	public LocalElement getSelection() {
		if (false == selections.isEmpty()) {
			return (LocalElement) selections.get(0);
		}
		return null;
	}

	class SelectChartsFromView extends Action {

		public SelectChartsFromView() {
			super("Select Charts From View", Action.AS_PUSH_BUTTON);
		}

		@Override
		public void run() {
			try {
				//get view from role preference
				LocalView view = (LocalView) localRolePreference.getElement(BEViewsElementNames.VIEW);
				//get charts from view
				List<LocalElement> components = view.getComponents(BEViewsElementNames.getChartOrTextComponentTypes());
				//unselect all from viewer
				viewer.getUnselectAllAction().run();
				//set selection as components
				viewer.setSelection(new StructuredSelection(components.toArray()));
			} catch (Exception e) {
				exHandler.logAndAlert(getText(), exHandler.createStatus(IStatus.WARNING, "could not select charts from view", e));
			}
		}
	}

}