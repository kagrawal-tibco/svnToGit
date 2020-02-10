package com.tibco.cep.decision.table.preferences;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.tibco.cep.decision.table.ui.DecisionTableUIPlugin;



public class ProblemsPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {
	public static final String Id = "com.tibco.cep.decision.table.problemsPreferencePage";
	private Text 		maxItemsText;
	private Combo 		sortOrderText;
	private Button 		showResourceButton;
	private Button 		activateViewButton;

	private static final int SEVERITY 		= 0;
	private static final int DESCRIPTION	= 1;
	private static final int RESOURCE 		= 2;
	private static final int PROBLEMCODE	= 3;
	
	private static String[] sortOrders 			= new String[4];
	private static String[] sortOrderPrefValues = new String[4];
	static {
		sortOrders[SEVERITY] 	= Messages.getString("ProblemsPreferencePage.Severity"); //$NON-NLS-1$
		sortOrders[DESCRIPTION] = Messages.getString("ProblemsPreferencePage.Desc"); //$NON-NLS-1$
		sortOrders[RESOURCE] 	= Messages.getString("ProblemsPreferencePage.Resource"); //$NON-NLS-1$
		sortOrders[PROBLEMCODE] = Messages.getString("ProblemsPreferencePage.ProbCode"); //$NON-NLS-1$
		
		sortOrderPrefValues[SEVERITY] 	 = "SEVERITY"; //$NON-NLS-1$
		sortOrderPrefValues[DESCRIPTION] = "DESCRIPTION"; //$NON-NLS-1$
		sortOrderPrefValues[RESOURCE] 	 = "RESOURCE"; //$NON-NLS-1$
		sortOrderPrefValues[PROBLEMCODE] = "PROBLEMCODE"; //$NON-NLS-1$
		
	}
	
	@Override
	protected Control createContents(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		Group problemsViewGroup = new Group(composite, SWT.NULL);
		problemsViewGroup.setText(Messages.getString("ProblemsPreferencePage.ProbView")); //$NON-NLS-1$
		problemsViewGroup.setLayout(new GridLayout(2, false));
		problemsViewGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		// max number of items
		Label maxItemsLabel = new Label(problemsViewGroup, SWT.NULL);
		maxItemsLabel.setText(Messages.getString("ProblemsPreferencePage.MaxItems")); //$NON-NLS-1$
		maxItemsText = new Text(problemsViewGroup, SWT.BORDER);
		maxItemsText.setText("100"); //$NON-NLS-1$
		GridData maxItemsData = new GridData(GridData.FILL_HORIZONTAL);
		maxItemsText.setLayoutData(maxItemsData);
		maxItemsText.addModifyListener(new ModifyListener() {
		
			public void modifyText(ModifyEvent e) {
				try {
					int i = Integer.valueOf(maxItemsText.getText());
					if (i <= 0) {
						setErrorMessage(Messages.getString("ProblemsPreferencePage.error.MaxItemsGreaterThan0")); //$NON-NLS-1$
						return;
					}
				} catch (NumberFormatException ex) {
					setErrorMessage(Messages.getString("ProblemsPreferencePage.error.EnterValidInteger")); //$NON-NLS-1$
					return;
				}
				setErrorMessage(null);
			}
		
		});
		
		// sort order
		Label sortOrderLabel = new Label(problemsViewGroup, SWT.NULL);
		sortOrderLabel.setText(Messages.getString("ProblemsPreferencePage.SortBy")); //$NON-NLS-1$
		sortOrderText = new Combo(problemsViewGroup, SWT.BORDER | SWT.READ_ONLY);
		sortOrderText.setItems(sortOrders);
		GridData sortOrderData = new GridData(GridData.FILL_HORIZONTAL);
		sortOrderText.setLayoutData(sortOrderData);
		
		// show Resource column
		showResourceButton = new Button(problemsViewGroup, SWT.CHECK);
		showResourceButton.setText(Messages.getString("ProblemsPreferencePage.ShowResourceColumn")); //$NON-NLS-1$
		GridData rbData = new GridData();
		rbData.horizontalSpan = 2;
		showResourceButton.setLayoutData(rbData);
		
		// activate on new Problem
		activateViewButton = new Button(problemsViewGroup, SWT.CHECK);
		activateViewButton.setText(Messages.getString("ProblemsPreferencePage.Activate")); //$NON-NLS-1$
		GridData avData = new GridData();
		avData.horizontalSpan = 2;
		activateViewButton.setLayoutData(avData);
		
		loadValues();
		return composite;
	}

	private void loadValues() {
		IPreferenceStore store = getPreferenceStore();
		
		activateViewButton.setSelection(store.getBoolean(PreferenceConstants.PROBLEMS_ACTIVATE_ON_PROB));
		showResourceButton.setSelection(store.getBoolean(PreferenceConstants.PROBLEMS_SHOW_RESOURCE));
		
		int maxItems = store.getInt(PreferenceConstants.PROBLEMS_MAX_ITEMS);
		if (maxItems == 0) {
			maxItems = store.getDefaultInt(PreferenceConstants.PROBLEMS_MAX_ITEMS);
		}
		if (maxItems > 0) {
			String maxItemsString = String.valueOf(maxItems);
			maxItemsText.setText(maxItemsString);
		}
		
		String sortOrder = store.getString(PreferenceConstants.PROBLEMS_SORT_ORDER);
		if (sortOrderPrefValues[SEVERITY].equals(sortOrder)) {
			sortOrderText.select(SEVERITY);
		} else if (sortOrderPrefValues[DESCRIPTION].equals(sortOrder)) {
			sortOrderText.select(DESCRIPTION);
		} else if (sortOrderPrefValues[RESOURCE].equals(sortOrder)) {
			sortOrderText.select(RESOURCE);
		} else if (sortOrderPrefValues[PROBLEMCODE].equals(sortOrder)) {
			sortOrderText.select(PROBLEMCODE);
		}
	}

	public void init(IWorkbench workbench) {
		setPreferenceStore(DecisionTableUIPlugin.getDefault().getPreferenceStore());
	}

	@Override
	public boolean performOk() {
		IPreferenceStore store = getPreferenceStore();
		
		if (store.getBoolean(PreferenceConstants.PROBLEMS_ACTIVATE_ON_PROB)
				!= activateViewButton.getSelection()) {
			store.setValue(PreferenceConstants.PROBLEMS_ACTIVATE_ON_PROB, activateViewButton.getSelection());
		}
		if (store.getBoolean(PreferenceConstants.PROBLEMS_SHOW_RESOURCE)
				!= showResourceButton.getSelection()) {
			store.setValue(PreferenceConstants.PROBLEMS_SHOW_RESOURCE, showResourceButton.getSelection());
		}
		
		int maxItems = Integer.valueOf(maxItemsText.getText());
		if (maxItems != store.getInt(PreferenceConstants.PROBLEMS_MAX_ITEMS)) {
			store.setValue(PreferenceConstants.PROBLEMS_MAX_ITEMS, maxItems);
		}

		int sortOrderIndex = sortOrderText.getSelectionIndex();
		if (!sortOrderPrefValues[sortOrderIndex].equals(store.getString(PreferenceConstants.PROBLEMS_SORT_ORDER))) {
			store.setValue(PreferenceConstants.PROBLEMS_SORT_ORDER, sortOrderPrefValues[sortOrderIndex]);
		}

		return true;
	}

	@Override
	protected void performDefaults() {
		super.performDefaults();
		setDefaultValues();
		loadValues();
	}

	private void setDefaultValues() {
		IPreferenceStore store = getPreferenceStore();
		
		if (store.getBoolean(PreferenceConstants.PROBLEMS_ACTIVATE_ON_PROB)
				!= store.getDefaultBoolean(PreferenceConstants.PROBLEMS_ACTIVATE_ON_PROB)) {
			store.setValue(PreferenceConstants.PROBLEMS_ACTIVATE_ON_PROB, store.getDefaultBoolean(PreferenceConstants.PROBLEMS_ACTIVATE_ON_PROB));
		}
		if (store.getBoolean(PreferenceConstants.PROBLEMS_SHOW_RESOURCE)
				!= store.getDefaultBoolean(PreferenceConstants.PROBLEMS_SHOW_RESOURCE)) {
			store.setValue(PreferenceConstants.PROBLEMS_SHOW_RESOURCE, store.getDefaultBoolean(PreferenceConstants.PROBLEMS_SHOW_RESOURCE));
		}
		
		int maxItems = store.getDefaultInt(PreferenceConstants.PROBLEMS_MAX_ITEMS);
		if (maxItems != store.getInt(PreferenceConstants.PROBLEMS_MAX_ITEMS)) {
			store.setValue(PreferenceConstants.PROBLEMS_MAX_ITEMS, maxItems);
		}

		String sortOrder = store.getDefaultString(PreferenceConstants.PROBLEMS_SORT_ORDER);
		if (!sortOrder.equals(store.getString(PreferenceConstants.PROBLEMS_SORT_ORDER))) {
			store.setValue(PreferenceConstants.PROBLEMS_SORT_ORDER, sortOrder);
		}

	}

}
