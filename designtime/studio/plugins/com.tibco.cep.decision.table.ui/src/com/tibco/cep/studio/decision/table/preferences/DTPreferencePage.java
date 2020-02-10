package com.tibco.cep.studio.decision.table.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.tibco.cep.studio.decision.table.ui.DecisionTableUIPlugin;
import com.tibco.cep.studio.decision.table.utils.DecisionTableUtil;

public class DTPreferencePage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {
	public static final String Id = "com.tibco.cep.decision.table.dtPreferencePage";
	private Group tableGroup;
	private Group ruleIDGroup;

	private BooleanFieldEditor showDomainDescriptionEditor;
	private BooleanFieldEditor showColumnAliasEditor;
	private BooleanFieldEditor autoMergeViewEditor;
	private BooleanFieldEditor useExistingIDsAtImportEditor;
	private BooleanFieldEditor useAliasInExportEditor;
	private BooleanFieldEditor autoResizeColumnEditor;
	private BooleanFieldEditor autoResizeRowHeightEditor;
	private BooleanFieldEditor autoUpdateDTOnVRFArgumentChange;
	private BooleanFieldEditor showColumnFilterEditor;
	private BooleanFieldEditor columnGroupEditor;
	private BooleanFieldEditor showTextEditor;
	private BooleanFieldEditor reuseRuleIDsEditor;
	private BooleanFieldEditor sectionEditor;
	private Button idsFollowRuleOrderButton;
	private Button maintainRuleIdsButton;

	public DTPreferencePage() {
		super(GRID);
		setPreferenceStore(DecisionTableUIPlugin.getDefault()
				.getPreferenceStore());
	}

	@Override
	protected void createFieldEditors() {

		tableGroup = new Group(getFieldEditorParent(), SWT.NULL);
		tableGroup.setText(Messages.getString("GROUP_TABLEVIEW"));
		tableGroup.setLayout(new GridLayout(1, false));
		tableGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL
				| GridData.GRAB_HORIZONTAL));
		showColumnAliasEditor = new BooleanFieldEditor(
				PreferenceConstants.SHOW_COLUMN_ALIAS,
				Messages.getString("SHOW_COLUMN_ALIAS"), tableGroup);
		addField(showColumnAliasEditor);
		autoResizeColumnEditor = new BooleanFieldEditor(
				PreferenceConstants.AUTO_RESIZE_COLUMN,
				Messages.getString("AUTO_RESIZE_COLUMN"), tableGroup);
		addField(autoResizeColumnEditor);
		autoResizeRowHeightEditor = new BooleanFieldEditor(
				PreferenceConstants.AUTO_RESIZE_ROWS,
				Messages.getString("AUTO_RESIZE_ROWS"), tableGroup);
		addField(autoResizeRowHeightEditor);
		autoMergeViewEditor = new BooleanFieldEditor(
				PreferenceConstants.AUTO_MERGE_VIEW,
				Messages.getString("AUTO_MERGE_VIEW"), tableGroup);
		addField(autoMergeViewEditor);

		showTextEditor = new BooleanFieldEditor(PreferenceConstants.SHOW_ALIAS,
				Messages.getString("SHOW_TEXT"), tableGroup);
		addField(showTextEditor);
		showDomainDescriptionEditor = new BooleanFieldEditor(
				PreferenceConstants.SHOW_DOMAIN_DESCRIPTION,
				Messages.getString("SHOW_DOMAIN_DESCRIPTION"), tableGroup);
		addField(showDomainDescriptionEditor);
		showColumnFilterEditor = new BooleanFieldEditor(
				PreferenceConstants.SHOW_COLUMN_FILTER,
				Messages.getString("SHOW_COLUMN_FILTER"), tableGroup);
		addField(showColumnFilterEditor);

		useAliasInExportEditor = new BooleanFieldEditor(
				PreferenceConstants.USE_ALIAS_IN_EXPORT,
				Messages.getString("USE_ALIAS_IN_EXPORT"), tableGroup);
		addField(useAliasInExportEditor);
		
		columnGroupEditor = new BooleanFieldEditor(
				PreferenceConstants.SHOW_COLUMN_GROUPS,
				Messages.getString("SHOW_VER_COND_AREA_STRING"), tableGroup);
		addField(columnGroupEditor);
		
		/*autoUpdateDTOnVRFArgumentChange = new BooleanFieldEditor(
				PreferenceConstants.AUTO_UPDATE_DT_VRF_ARGUMENT_CHANGE,
				Messages.getString("AUTO_UPDATE_DT_VRF_ARGUMENT_CHANGE"),
				tableGroup);
		addField(autoUpdateDTOnVRFArgumentChange);

		*/

		ruleIDGroup = new Group(getFieldEditorParent(), SWT.WRAP);
		ruleIDGroup.setText(Messages.getString("GROUP_RULEID"));
		ruleIDGroup.setLayout(new GridLayout(1, false));
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		ruleIDGroup.setLayoutData(gridData);
		

		useExistingIDsAtImportEditor = new BooleanFieldEditor(
				PreferenceConstants.EXISTING_ID_IMPORT,
				Messages.getString("EXISTING_ID_IMPORT"), ruleIDGroup);
		addField(useExistingIDsAtImportEditor);

		reuseRuleIDsEditor = new BooleanFieldEditor(
				PreferenceConstants.REUSE_DELETED_RULE_IDS,
				Messages.getString("REUSE_DELETED_IDS"), ruleIDGroup);
		reuseRuleIDsEditor.fillIntoGrid(ruleIDGroup, 1);
		addField(reuseRuleIDsEditor);

		idsFollowRuleOrderButton = new Button(ruleIDGroup, SWT.WRAP | SWT.RADIO);
		idsFollowRuleOrderButton.setText(Messages.getString("IDS_FOLLOW_RULE_ORDER"));
		idsFollowRuleOrderButton.setFont(ruleIDGroup.getFont());
		GridData d1 = new GridData();
		d1.widthHint = 500;
		idsFollowRuleOrderButton.setLayoutData(d1);
		idsFollowRuleOrderButton.setSelection(!getPreferenceStore().getBoolean(PreferenceConstants.ALWAYS_MAINTAIN_RULE_IDS));
		
		maintainRuleIdsButton = new Button(ruleIDGroup, SWT.WRAP | SWT.RADIO);
		maintainRuleIdsButton.setText(Messages.getString("ALWAYS_MAINTAIN_RULE_IDS"));
		maintainRuleIdsButton.setFont(ruleIDGroup.getFont());
		GridData d = new GridData();
		d.widthHint = 500;
		maintainRuleIdsButton.setLayoutData(d);
		maintainRuleIdsButton.setSelection(getPreferenceStore().getBoolean(PreferenceConstants.ALWAYS_MAINTAIN_RULE_IDS));
		
//		String[][] labelAndValues = new String[][] { {Messages.getString("IDS_FOLLOW_RULE_ORDER"), "false"}, {Messages.getString("ALWAYS_MAINTAIN_RULE_IDS"), "true"}};
//		RadioGroupFieldEditor radioEditor = new RadioGroupFieldEditor(PreferenceConstants.ALWAYS_MAINTAIN_RULE_IDS, "ID Order", 1, labelAndValues, ruleIDGroup, true);
//		addField(radioEditor);
		
		/*
		 * Group advGroup = new Group(getFieldEditorParent(),
		 * SWT.SHADOW_ETCHED_OUT);
		 * advGroup.setText(Messages.getString("GROUP_ADVANCED"));
		 * advGroup.setLayout( new GridLayout(1, false));
		 * advGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL |
		 * GridData.GRAB_HORIZONTAL)); sectionEditor = new
		 * BooleanFieldEditor(PreferenceConstants.EDITOR_SECTIONS,
		 * Messages.getString("EDITOR_SECTIONS"), advGroup);
		 * addField(sectionEditor);
		 */

		// Group advGroup = new Group(getFieldEditorParent(),
		// SWT.SHADOW_ETCHED_OUT);
		// advGroup.setText(Messages.getString("GROUP_ADVANCED"));
		// advGroup.setLayout( new GridLayout(1, false));
		// advGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL |
		// GridData.GRAB_HORIZONTAL));
		// addField(new BooleanFieldEditor(PreferenceConstants.EDITOR_SECTIONS,
		// Messages.getString("EDITOR_SECTIONS"), advGroup));

		// Composite composite = getFieldEditorParent();
		// titledBorderButton = getButtonWithText(composite,
		// Messages.getString("SHOW_TITLED_BORDER"));
		// if (titledBorderButton != null) {
		// titledBorderButton.addSelectionListener(new SelectionListener() {
		//
		// public void widgetDefaultSelected(SelectionEvent e) {
		//
		// }
		//
		// public void widgetSelected(SelectionEvent e) {
		// if (e.getSource() instanceof Button) {
		// Button button = (Button) e.getSource();
		// if (!button.getSelection()) {
		// showTitledBorderEditor.setEnabled(false,
		// tableGroup);
		// columnGroupEditor.setEnabled(false,
		// tableGroup);
		// } else {
		// showTitledBorderEditor.setEnabled(true,
		// tableGroup);
		// columnGroupEditor.setEnabled(true,
		// tableGroup);
		// }
		// }
		//
		// }
		//
		// });

		// }

	}

	// private Button getButtonWithText(Composite composite,
	// String buttonText) {
	// Control[] childControls = composite.getChildren();
	// for (int i = 0; i < childControls.length; ++i) {
	// Control control = childControls[i];
	// if (control instanceof Button) {
	// Button button = (Button) control;
	// if (button.getText().equals(Messages.getString("SHOW_TITLED_BORDER"))) {
	// return button;
	// }
	// } else if (control instanceof Group) {
	// Button button = getButtonWithText((Group)control, buttonText);
	// if (button != null) {
	// return button;
	// }
	// }
	// }
	// return null;
	// }

	@Override
	public void init(IWorkbench workbench) {

	}

	@Override
	protected void performApply() {
		// TODO Auto-generated method stub
		super.performApply();
		DecisionTableUtil
				.updateAutoMerge(autoMergeViewEditor.getBooleanValue());
		DecisionTableUtil.updateColumnFilterVisibility(showColumnFilterEditor
				.getBooleanValue());
	}

	@Override
	protected void performDefaults() {
		super.performDefaults();
		idsFollowRuleOrderButton.setSelection(!getPreferenceStore().getDefaultBoolean(PreferenceConstants.ALWAYS_MAINTAIN_RULE_IDS));
		maintainRuleIdsButton.setSelection(getPreferenceStore().getDefaultBoolean(PreferenceConstants.ALWAYS_MAINTAIN_RULE_IDS));
		// actionAreaEditor.setEnabled(true,
		// tableGroup);
		// columnGroupEditor.setEnabled(true,
		// tableGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.FieldEditorPreferencePage#performOk()
	 */
	@Override
	public boolean performOk() {

		boolean showColumnAlias = showColumnAliasEditor.getBooleanValue();
		getPreferenceStore().setValue(PreferenceConstants.SHOW_COLUMN_ALIAS,
				showColumnAlias);

		boolean showDomainDesription = showDomainDescriptionEditor
				.getBooleanValue();
		getPreferenceStore().setValue(
				PreferenceConstants.SHOW_DOMAIN_DESCRIPTION,
				showDomainDesription);

		boolean autoMergeView = autoMergeViewEditor.getBooleanValue();
		getPreferenceStore().setValue(PreferenceConstants.AUTO_MERGE_VIEW,
				autoMergeView);

		boolean showColumnFilter = showColumnFilterEditor.getBooleanValue();
		getPreferenceStore().setValue(PreferenceConstants.SHOW_COLUMN_FILTER,
				showColumnFilter);

		boolean useExistingIDsAtImport = useExistingIDsAtImportEditor
				.getBooleanValue();
		getPreferenceStore().setValue(PreferenceConstants.EXISTING_ID_IMPORT,
				useExistingIDsAtImport);

		boolean useAliasInExport = useAliasInExportEditor.getBooleanValue();
		getPreferenceStore().setValue(PreferenceConstants.USE_ALIAS_IN_EXPORT,
				useAliasInExport);

		boolean autoResizeColumn = autoResizeColumnEditor.getBooleanValue();
		getPreferenceStore().setValue(PreferenceConstants.AUTO_RESIZE_COLUMN,
				autoResizeColumn);

		boolean autoResizeRows = autoResizeRowHeightEditor.getBooleanValue();
		getPreferenceStore().setValue(PreferenceConstants.AUTO_RESIZE_ROWS,
				autoResizeRows);
		boolean showConditionArea = columnGroupEditor.getBooleanValue();
		getPreferenceStore().setValue(PreferenceConstants.SHOW_COLUMN_GROUPS,
				showConditionArea);
		
		getPreferenceStore().setValue(PreferenceConstants.ALWAYS_MAINTAIN_RULE_IDS, maintainRuleIdsButton.getSelection());
	/*	boolean autoUpdateDT = autoUpdateDTOnVRFArgumentChange
				.getBooleanValue();
		InstanceScope.INSTANCE.getNode(StudioCorePlugin.PLUGIN_ID).putBoolean(PreferenceConstants.AUTO_UPDATE_DT_VRF_ARGUMENT_CHANGE, autoUpdateDT);
		
		
	*/

		// boolean sectionNonResize = sectionEditor.getBooleanValue();
		// getPreferenceStore().setValue(PreferenceConstants.EDITOR_SECTIONS,
		// sectionNonResize);
		return super.performOk();

	}

}
