package com.tibco.cep.studio.decision.table.preferences;

import static com.tibco.cep.studio.decision.table.preferences.PreferenceConstants.ANALYZER_HIGHLIGHT_PARTIAL_RANGES;
import static com.tibco.cep.studio.decision.table.preferences.PreferenceConstants.ANALYZER_USE_DOMAINMODEL;
import static com.tibco.cep.studio.decision.table.preferences.PreferenceConstants.RUN_ANALYZER;

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

import com.tibco.cep.studio.decision.table.ui.DecisionTableUIPlugin;
import com.tibco.cep.studio.decision.table.ui.utils.Messages;

/**
 * This class is used to create the Analyzer page in Preferences.
 * 
 * @author dijadhav
 * 
 */
public class DTAnalyzerPreferencePage extends FieldEditorPreferencePage implements
IWorkbenchPreferencePage {

	public static final String Id = "com.tibco.cep.decision.table.dtAnalyzer";

	private Button fHighlightPartialRangesButton;
	private Button m_UseDomainModelButton;
	private Button runAnalyzerButton;

	@Override
	protected Control createContents(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));	
		createHighlightPartialRangeButton(composite);
		createDomainModelButton(composite);
		createTableAnalyzerOption(composite);
		return composite;
	}

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

	public void init(IWorkbench workbench) {
		setPreferenceStore(DecisionTableUIPlugin.getDefault()
				.getPreferenceStore());
	}

	@Override
	protected void performDefaults() {
		super.performDefaults();
		fHighlightPartialRangesButton.setSelection(getPreferenceStore().getDefaultBoolean(
				ANALYZER_HIGHLIGHT_PARTIAL_RANGES));

		m_UseDomainModelButton.setSelection(getPreferenceStore().getDefaultBoolean(
				ANALYZER_USE_DOMAINMODEL));

		runAnalyzerButton.setSelection(getPreferenceStore().getDefaultBoolean(
				RUN_ANALYZER));
	}
	@Override
	public boolean performOk() {
		super.performOk();
		getPreferenceStore().setValue(ANALYZER_HIGHLIGHT_PARTIAL_RANGES,
				fHighlightPartialRangesButton.getSelection());

		getPreferenceStore().setValue(ANALYZER_USE_DOMAINMODEL,
				m_UseDomainModelButton.getSelection());

		getPreferenceStore().setValue(RUN_ANALYZER,
				runAnalyzerButton.getSelection());

		return true;
	}

	@Override
	protected void createFieldEditors() {
		


	}

}