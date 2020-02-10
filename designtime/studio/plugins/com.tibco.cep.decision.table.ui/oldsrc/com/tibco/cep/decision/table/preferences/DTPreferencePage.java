package com.tibco.cep.decision.table.preferences;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.tibco.cep.decision.table.ui.DecisionTableUIPlugin;
import com.tibco.cep.decision.table.ui.utils.Messages;
import com.tibco.cep.decision.table.utils.DecisionTableUtil;
import com.tibco.cep.studio.core.StudioCorePlugin;

public class DTPreferencePage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {
	public static final String Id = "com.tibco.cep.decision.table.dtPreferencePage";
	private FieldEditor actionAreaEditor;
//	private FieldEditor horizontalConditionAreaEditor;
	private FieldEditor verticalConditionAreaEditor;
	private Button titledBorderButton;
	private Group tableGroup;
	
	private BooleanFieldEditor showColumnAliasEditor;
	private BooleanFieldEditor showDomainDescriptionEditor;
	private BooleanFieldEditor autoMergeViewEditor;
	private BooleanFieldEditor showColumnFilterEditor;
	private BooleanFieldEditor showTitledBorderEditor;
	private BooleanFieldEditor useExistingIDsAtImportEditor;
	private BooleanFieldEditor useAliasInExportEditor;
	private BooleanFieldEditor autoResizeColumnEditor;
	private BooleanFieldEditor autoResizeRowHeightEditor;
	private BooleanFieldEditor autoUpdateDTOnVRFArgumentChange;

	public DTPreferencePage() {
		super(GRID);
		setPreferenceStore(DecisionTableUIPlugin.getDefault().getPreferenceStore());
	}

	@Override
	protected void createFieldEditors() {

		tableGroup = new Group(getFieldEditorParent(), SWT.NULL);
		tableGroup.setText(Messages.getString("GROUP_TABLEVIEW"));
        tableGroup.setLayout( new GridLayout(1, false));
        tableGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));
        showColumnAliasEditor = new BooleanFieldEditor(PreferenceConstants.SHOW_COLUMN_ALIAS,
    			Messages.getString("SHOW_COLUMN_ALIAS"), tableGroup);
		addField(showColumnAliasEditor);
		autoResizeColumnEditor = new BooleanFieldEditor(PreferenceConstants.AUTO_RESIZE_COLUMN,
				Messages.getString("AUTO_RESIZE_COLUMN"), tableGroup);
		addField(autoResizeColumnEditor);
		autoResizeRowHeightEditor = new BooleanFieldEditor(PreferenceConstants.AUTO_RESIZE_COLUMN,
				Messages.getString("AUTO_RESIZE_ROWS"), tableGroup);
		addField(autoResizeRowHeightEditor);
		autoMergeViewEditor = new BooleanFieldEditor(PreferenceConstants.AUTO_MERGE_VIEW,
				Messages.getString("AUTO_MERGE_VIEW"), tableGroup);
		addField(autoMergeViewEditor);
		addField(new BooleanFieldEditor(PreferenceConstants.SHOW_ALIAS,
				Messages.getString("SHOW_TEXT"), tableGroup));
		showDomainDescriptionEditor = new BooleanFieldEditor(PreferenceConstants.SHOW_DOMAIN_DESCRIPTION,
				Messages.getString("SHOW_DOMAIN_DESCRIPTION"), tableGroup);
		addField(showDomainDescriptionEditor);
		showColumnFilterEditor = new BooleanFieldEditor(PreferenceConstants.SHOW_COLUMN_FILTER,
				Messages.getString("SHOW_COLUMN_FILTER"), tableGroup);
		addField(showColumnFilterEditor);
		showTitledBorderEditor = new BooleanFieldEditor(PreferenceConstants.SHOW_TITLED_BORDER,
				Messages.getString("SHOW_TITLED_BORDER"), tableGroup);
		addField(showTitledBorderEditor);
		useExistingIDsAtImportEditor = new BooleanFieldEditor(PreferenceConstants.EXISTING_ID_IMPORT,
				Messages.getString("EXISTING_ID_IMPORT"), tableGroup);
		addField(useExistingIDsAtImportEditor);
		useAliasInExportEditor = new BooleanFieldEditor(PreferenceConstants.USE_ALIAS_IN_EXPORT,
				Messages.getString("USE_ALIAS_IN_EXPORT"), tableGroup);
		addField(useAliasInExportEditor);
		autoUpdateDTOnVRFArgumentChange = new BooleanFieldEditor(PreferenceConstants.AUTO_UPDATE_DT_VRF_ARGUMENT_CHANGE, 
				Messages.getString("AUTO_UPDATE_DT_VRF_ARGUMENT_CHANGE"), tableGroup);
		addField(autoUpdateDTOnVRFArgumentChange);
		
//		FieldEditor titledBorderEditor = new BooleanFieldEditor(
//				PreferenceConstants.SHOW_TITLED_BORDER,
//				Messages.SHOW_TITLED_BORDER, tableGroup);
//		addField(titledBorderEditor);
//		actionAreaEditor = new BooleanFieldEditor(
//				PreferenceConstants.SHOW_ACTION_AREA_STRING,
//				Messages.SHOW_ACTION_AREA_STRING, tableGroup);
//		addField(actionAreaEditor);
//		horizontalConditionAreaEditor = new BooleanFieldEditor(
//				PreferenceConstants.SHOW_HORIZONTAL_COND_AREA_STRING,
//				Messages.SHOW_HORIZONTAL_COND_AREA_STRING, tableGroup);
//		addField(horizontalConditionAreaEditor);
		verticalConditionAreaEditor = new BooleanFieldEditor(
				PreferenceConstants.SHOW_VER_COND_AREA_STRING,
				Messages.getString("SHOW_VER_COND_AREA_STRING"), tableGroup);
		addField(verticalConditionAreaEditor);
		
//		if (getPreferenceStore().getBoolean(PreferenceConstants.SHOW_TITLED_BORDER)) {
//			actionAreaEditor.setEnabled(true, tableGroup);
//			verticalConditionAreaEditor.setEnabled(true, tableGroup);
//		} else {
//			actionAreaEditor.setEnabled(false, tableGroup);
//			verticalConditionAreaEditor.setEnabled(false, tableGroup);
//		}
		
//		String[][] labelAndValues = {
//				{ Messages.getString("SHOW_ROLL_OVER_TOOL_BAR"),
//						PreferenceConstants.SHOW_ROLL_OVER_TOOL_BAR },
//				{ Messages.getString("SHOW_CONTEXT_MENU"),
//				PreferenceConstants.SHOW_CONTEXT_MENU } };
//
//		RadioGroupFieldEditor menuFieldEditor = new RadioGroupFieldEditor(
//				PreferenceConstants.MENU_OPTION, Messages.getString("MENU_OPTION"), 4,
//				labelAndValues, getFieldEditorParent(), true);
//		addField(menuFieldEditor);

//		String[][] filterLabelAndValues = {
//				{ Messages.getString("FILTER_BUTTON_ON"),
//						PreferenceConstants.FILTER_BUTTON_ON },
//				{ Messages.getString("FILTER_BUTTON_OFF"),
//						PreferenceConstants.FILTER_BUTTON_OFF } };

//		RadioGroupFieldEditor condFilterFieldEditor = new RadioGroupFieldEditor(
//				PreferenceConstants.CONDITION_FIELD_FILTER,
//				Messages.CONDITION_FIELD_FILTER, 4, filterLabelAndValues,
//				getFieldEditorParent(), true);
//		addField(condFilterFieldEditor);
//
//		RadioGroupFieldEditor actionFilterFieldEditor = new RadioGroupFieldEditor(
//				PreferenceConstants.ACTION_FIELD_FILTER,
//				Messages.ACTION_FIELD_FILTER, 4, filterLabelAndValues,
//				getFieldEditorParent(), true);
//		addField(actionFilterFieldEditor);
		
		Group advGroup = new Group(getFieldEditorParent(), SWT.SHADOW_ETCHED_OUT);
		advGroup.setText(Messages.getString("GROUP_ADVANCED"));
        advGroup.setLayout( new GridLayout(1, false));
        advGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));
		addField(new BooleanFieldEditor(PreferenceConstants.EDITOR_SECTIONS,
				Messages.getString("EDITOR_SECTIONS"), advGroup));
		
		// CR 1-91G6CS
//		addField(new BooleanFieldEditor(PreferenceConstants.HORIZONTAL_VERTICAL_DECISION_VIEW,
//				Messages.HORIZONTAL_VERTICAL_DECISION_VIEW, advGroup));
		
//		addField(new BooleanFieldEditor(PreferenceConstants.SHOW_COLUMN_ID,
//				Messages.SHOW_COLUMN_ID, advGroup));
		
//		addField(new BooleanFieldEditor(PreferenceConstants.EXISTING_ID_IMPORT,
//				Messages.EXISTING_ID_IMPORT, advGroup));
		
//		addField(new BooleanFieldEditor(PreferenceConstants.VALIDATE_DURING_COMMIT,
//				Messages.VALIDATE_DURING_COMMIT, advGroup));
		
//		addField(new BooleanFieldEditor(PreferenceConstants.AUTO_DEPLOY_BEFORE_TEST,
//				Messages.AUTO_DEPLOY_BEFORE_TEST, advGroup));
//		
//		addField(new BooleanFieldEditor(PreferenceConstants.SHOW_RULES,
//				Messages.SHOW_RULES, advGroup));
		
		Composite composite = getFieldEditorParent();
		titledBorderButton = getButtonWithText(composite, Messages.getString("SHOW_TITLED_BORDER"));
		if (titledBorderButton != null) {
			titledBorderButton.addSelectionListener(new SelectionListener() {

				public void widgetDefaultSelected(SelectionEvent e) {
					// TODO Auto-generated method stub

				}

				public void widgetSelected(SelectionEvent e) {
					// TODO Auto-generated method stub
					if (e.getSource() instanceof Button) {
						Button button = (Button) e.getSource();
						if (!button.getSelection()) {
							showTitledBorderEditor.setEnabled(false,
									tableGroup);
//							horizontalConditionAreaEditor.setEnabled(false,
//									tableGroup);
							verticalConditionAreaEditor.setEnabled(false,
									tableGroup);	
						} else {
							showTitledBorderEditor.setEnabled(true,
									tableGroup);
//							horizontalConditionAreaEditor.setEnabled(true,
//									tableGroup);
							verticalConditionAreaEditor.setEnabled(true,
									tableGroup);	
						}
					}

				}

			});

		}

	}

	private Button getButtonWithText(Composite composite,
			String buttonText) {
		Control[] childControls = composite.getChildren();
		for (int i = 0; i < childControls.length; ++i) {
			Control control = childControls[i];
			if (control instanceof Button) {
				Button button = (Button) control;
				if (button.getText().equals(Messages.getString("SHOW_TITLED_BORDER"))) {
					return button;
				}
			} else if (control instanceof Group) {
				Button button = getButtonWithText((Group)control, buttonText);
				if (button != null) {
					return button;
				}
			}
		}
		return null;
	}

	public void init(IWorkbench workbench) {
		// TODO Auto-generated method stub
		

	}

	@Override
	protected void performDefaults() {
		super.performDefaults();
		actionAreaEditor.setEnabled(true,
				tableGroup);
//		horizontalConditionAreaEditor.setEnabled(true,
//				tableGroup);
		verticalConditionAreaEditor.setEnabled(true,
				tableGroup);	
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.preference.FieldEditorPreferencePage#performOk()
	 */
	@Override
	public boolean performOk() {
		super.performOk();
		boolean showColumnAlias = showColumnAliasEditor.getBooleanValue();
		getPreferenceStore().setValue(PreferenceConstants.SHOW_COLUMN_ALIAS, showColumnAlias);
		
		boolean showDomainDesription = showDomainDescriptionEditor.getBooleanValue();
		getPreferenceStore().setValue(PreferenceConstants.SHOW_DOMAIN_DESCRIPTION, showDomainDesription);
		
		boolean autoMergeView = autoMergeViewEditor.getBooleanValue();
		getPreferenceStore().setValue(PreferenceConstants.AUTO_MERGE_VIEW, autoMergeView);
		DecisionTableUtil.updateAutoMerge(autoMergeView);
		
		boolean showColumnFilter = showColumnFilterEditor.getBooleanValue();
		getPreferenceStore().setValue(PreferenceConstants.SHOW_COLUMN_FILTER, showColumnFilter);
		DecisionTableUtil.updateColumnFilterVisibility();
		
		boolean showTitledBorder = showTitledBorderEditor.getBooleanValue();
		getPreferenceStore().setValue(PreferenceConstants.SHOW_TITLED_BORDER, showTitledBorder);
		
		boolean useExistingIDsAtImport = useExistingIDsAtImportEditor.getBooleanValue();
		getPreferenceStore().setValue(PreferenceConstants.EXISTING_ID_IMPORT, useExistingIDsAtImport);
		
		boolean useAliasInExport = useAliasInExportEditor.getBooleanValue();
		getPreferenceStore().setValue(PreferenceConstants.USE_ALIAS_IN_EXPORT, useAliasInExport);
		
		boolean autoResizeColumn = autoResizeColumnEditor.getBooleanValue();
		getPreferenceStore().setValue(PreferenceConstants.AUTO_RESIZE_COLUMN, autoResizeColumn);
		
		boolean autoResizeRows = autoResizeRowHeightEditor.getBooleanValue();
		getPreferenceStore().setValue(PreferenceConstants.AUTO_RESIZE_ROWS, autoResizeRows);
		
		
		boolean autoUpdateDT = autoUpdateDTOnVRFArgumentChange.getBooleanValue();
//		StudioCorePlugin.getDefault().getPluginPreferences().setValue(PreferenceConstants.AUTO_UPDATE_DT_VRF_ARGUMENT_CHANGE, autoUpdateDT);
		InstanceScope.INSTANCE.getNode(StudioCorePlugin.PLUGIN_ID).putBoolean(PreferenceConstants.AUTO_UPDATE_DT_VRF_ARGUMENT_CHANGE, autoUpdateDT);
		
		return true;
		
	}
}
