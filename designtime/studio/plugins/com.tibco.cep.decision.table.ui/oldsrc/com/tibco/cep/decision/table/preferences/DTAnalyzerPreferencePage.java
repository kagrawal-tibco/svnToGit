package com.tibco.cep.decision.table.preferences;

import static com.tibco.cep.decision.table.preferences.PreferenceConstants.ANALYZER_HIGHLIGHT_PARTIAL_RANGES;
import static com.tibco.cep.decision.table.preferences.PreferenceConstants.ANALYZER_SHOW_TABLE_ANALYZER_VIEW;
import static com.tibco.cep.decision.table.preferences.PreferenceConstants.ANALYZER_USE_DOMAINMODEL;
import static com.tibco.cep.decision.table.preferences.PreferenceConstants.RUN_ANALYZER;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.tibco.cep.decision.table.ui.DecisionTableUIPlugin;
import com.tibco.cep.decision.table.ui.utils.Messages;

public class DTAnalyzerPreferencePage extends FieldEditorPreferencePage implements
IWorkbenchPreferencePage {

	public static final String Id = "com.tibco.cep.decision.table.dtAnalyzer";

	private Button fShowTableAnalyzerButton;
	private Button fHighlightPartialRangesButton;
	private Button m_UseDomainModelButton;
	private Button runAnalyzerButton;
	private IntegerFieldEditor intervalRangeValuesShowFieldEditor;
	//	private Button fIntelligentTestGenerationButton; // should this be a preference?  or automatic?

	@Override
	protected Control createContents(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
//Removed the following preference as it had no significance after refresh was added to the Table Analyzer
//		createShowTableButton(composite);
		createHighlightPartialRangeButton(composite);
		createDomainModelButton(composite);
		createTableAnalyzerOption(composite);
//		createIntervalGenerateTestData(composite);
		//		createIntelligentTestGenButton(composite);

		return composite;
	}


//	protected void createIntervalGenerateTestData(Composite composite) {
//		intervalRangeValuesShowFieldEditor = new IntegerFieldEditor(
//				PreferenceConstants.INTEGER_RANGE_VALUE, Messages.getString("Interval_Range_Values"), composite);
//		addField(intervalRangeValuesShowFieldEditor);
//		intervalRangeValuesShowFieldEditor.setStringValue(getPreferenceStore().getString(PreferenceConstants.INTEGER_RANGE_VALUE));
//	}



	/**
	 * Create the widget for the Rule viewing.
	 * 
	 * @param composite
	 */
	protected void createHighlightPartialRangeButton(Composite composite) {
		fHighlightPartialRangesButton = new Button(composite, SWT.CHECK);
		fHighlightPartialRangesButton.setText(Messages.getString("AnalyzeTable_highlight_partial_ranges"));
		fHighlightPartialRangesButton.setToolTipText(Messages.getString("AnalyzeTable_highlight_partial_ranges"));
		fHighlightPartialRangesButton.setSelection(getPreferenceStore().getBoolean(
				PreferenceConstants.ANALYZER_HIGHLIGHT_PARTIAL_RANGES));
	}

	/**
	 * Create the widget for the Rule viewing.
	 * 
	 * @param composite
	 */
	protected void createDomainModelButton(Composite composite) {
		m_UseDomainModelButton = new Button(composite, SWT.CHECK);
		m_UseDomainModelButton.setText(Messages.getString("AnalyzeTable_Use_Domain_Model"));
		m_UseDomainModelButton.setToolTipText(Messages.getString("AnalyzeTable_Use_Domain_Model"));
		m_UseDomainModelButton.setSelection(getPreferenceStore().getBoolean(
				PreferenceConstants.ANALYZER_USE_DOMAINMODEL));
	}

	/**
	 * Create the widget for the Table Analyzer.
	 * 
	 * @param composite
	 */
	protected void createTableAnalyzerOption(Composite composite) {
		runAnalyzerButton = new Button(composite, SWT.CHECK);
		runAnalyzerButton.setText(Messages.getString("RUN_ANALYZER"));
		runAnalyzerButton.setToolTipText(Messages.getString("RUN_ANALYZER"));
		runAnalyzerButton.setSelection(getPreferenceStore().getBoolean(
				PreferenceConstants.RUN_ANALYZER));
	}

	//	protected void createIntelligentTestGenButton(Composite composite) {
	//		fIntelligentTestGenerationButton = new Button(composite, SWT.CHECK);
	//		fIntelligentTestGenerationButton.setText(Messages.AnalyzeTable_intelligent_data_gen);
	//		fIntelligentTestGenerationButton.setToolTipText(Messages.AnalyzeTable_intelligent_data_gen);
	//		fIntelligentTestGenerationButton.setSelection(getPreferenceStore().getBoolean(
	//				PreferenceConstants.ANALYZER_INTELLIGENT_TEST_GEN));
	//	}

//	private void createShowTableButton(Composite composite) {
//		fShowTableAnalyzerButton = new Button(composite, SWT.CHECK);
//		fShowTableAnalyzerButton.setText(Messages.getString("AnalyzeTable_initially_show_analyzer_view"));
//		fShowTableAnalyzerButton.setToolTipText(Messages.getString("AnalyzeTable_initially_show_analyzer_view"));
//		fShowTableAnalyzerButton.setSelection(getPreferenceStore().getBoolean(
//				ANALYZER_SHOW_TABLE_ANALYZER_VIEW));
//	}


	public void init(IWorkbench workbench) {
		setPreferenceStore(DecisionTableUIPlugin.getDefault()
				.getPreferenceStore());
	}

	@Override
	protected void performDefaults() {
		super.performDefaults();
		fShowTableAnalyzerButton.setSelection(getPreferenceStore().getDefaultBoolean(
				ANALYZER_SHOW_TABLE_ANALYZER_VIEW));

		fHighlightPartialRangesButton.setSelection(getPreferenceStore().getDefaultBoolean(
				ANALYZER_HIGHLIGHT_PARTIAL_RANGES));

		m_UseDomainModelButton.setSelection(getPreferenceStore().getDefaultBoolean(
				ANALYZER_USE_DOMAINMODEL));

		runAnalyzerButton.setSelection(getPreferenceStore().getDefaultBoolean(
				RUN_ANALYZER));
		intervalRangeValuesShowFieldEditor.setStringValue("5");
		//		fIntelligentTestGenerationButton.setSelection(getPreferenceStore().getDefaultBoolean(
		//				PreferenceConstants.ANALYZER_INTELLIGENT_TEST_GEN));
	}

	@Override
	public boolean performOk() {
		super.performOk();
		getPreferenceStore().setValue(ANALYZER_SHOW_TABLE_ANALYZER_VIEW,
				fShowTableAnalyzerButton.getSelection());

		getPreferenceStore().setValue(ANALYZER_HIGHLIGHT_PARTIAL_RANGES,
				fHighlightPartialRangesButton.getSelection());

		getPreferenceStore().setValue(ANALYZER_USE_DOMAINMODEL,
				m_UseDomainModelButton.getSelection());

		getPreferenceStore().setValue(RUN_ANALYZER,
				runAnalyzerButton.getSelection());

//		getPreferenceStore().setValue(PreferenceConstants.INTEGER_RANGE_VALUE, intervalRangeValuesShowFieldEditor.getIntValue());
		//		getPreferenceStore().setValue(PreferenceConstants.ANALYZER_INTELLIGENT_TEST_GEN,
		//				fIntelligentTestGenerationButton.getSelection());
		return true;
	}

	@Override
	protected void createFieldEditors() {
		


	}

}