package com.tibco.cep.bpmn.ui.preferences;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.tibco.cep.bpmn.core.BpmnCoreConstants;
import com.tibco.cep.bpmn.model.designtime.utils.BpmnCommonModelUtils;
import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.bpmn.ui.Messages;

public class BpmnCodegenPreferencePage extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage {

	private StringFieldEditor fFolder;
	private StringFieldEditor processPrefix;
	private StringFieldEditor rulePrefix;
	private StringFieldEditor rulefunctionPrefix;
	private StringFieldEditor conceptPrefix;
	private StringFieldEditor eventPrefix;
	private StringFieldEditor timeeventPrefix;
	private StringFieldEditor scoreCardPrefix;


	public BpmnCodegenPreferencePage() {
		super(FieldEditorPreferencePage.GRID);
		//setPreferenceStore(BpmnCorePlugin.getDefault().getPluginPreferences());
	}

	

	@Override
	protected void createFieldEditors() {
		@SuppressWarnings("unused")
		Composite parent = getFieldEditorParent();
		// do nothing

	}
	
	@Override
	protected Control createContents(Composite parent) {
		Composite comp = createComposite(parent, 3, 1, GridData.FILL_HORIZONTAL);
		this.fFolder = new StringFieldEditor(BpmnCoreConstants.PREF_CODEGEN_FOLDER,	
				Messages.getString(BpmnCoreConstants.PREF_LABEL_CODEGEN_FOLDER), comp);
		addField(fFolder);
		
		this.processPrefix = new StringFieldEditor(BpmnCoreConstants.PREF_CODEGEN_PROCESS_PREFIX,	
				Messages.getString(BpmnCoreConstants.PREF_LABEL_CODEGEN_PROCESS_PREFIX), comp);
		addField(processPrefix);
		
		this.rulePrefix = new StringFieldEditor(BpmnCoreConstants.PREF_CODEGEN_RULE_PREFIX,	
				Messages.getString(BpmnCoreConstants.PREF_LABEL_CODEGEN_RULE_PREFIX), comp);
		addField(rulePrefix);
		
		this.rulefunctionPrefix = new StringFieldEditor(BpmnCoreConstants.PREF_CODEGEN_RULE_FUNCTION_PREFIX,	
				Messages.getString(BpmnCoreConstants.PREF_LABEL_CODEGEN_RULE_FUNCTION_PREFIX), comp);
		addField(rulefunctionPrefix);
		
		this.conceptPrefix = new StringFieldEditor(BpmnCoreConstants.PREF_CODEGEN_CONCEPT_PREFIX,	
				Messages.getString(BpmnCoreConstants.PREF_LABEL_CODEGEN_CONCEPT_PREFIX), comp);
		addField(conceptPrefix);
		
		this.eventPrefix = new StringFieldEditor(BpmnCoreConstants.PREF_CODEGEN_EVENT_PREFIX,	
				Messages.getString(BpmnCoreConstants.PREF_LABEL_CODEGEN_EVENT_PREFIX), comp);
		addField(eventPrefix);
		
		this.timeeventPrefix = new StringFieldEditor(BpmnCoreConstants.PREF_CODEGEN_TIME_EVENT_PREFIX,	
				Messages.getString(BpmnCoreConstants.PREF_LABEL_CODEGEN_PROCESS_PREFIX), comp);
		addField(timeeventPrefix);
		
		this.scoreCardPrefix = new StringFieldEditor(BpmnCoreConstants.PREF_CODEGEN_SCORECARD_PREFIX,	
				Messages.getString(BpmnCoreConstants.PREF_LABEL_CODEGEN_SCORECARD_PREFIX), comp);
		addField(scoreCardPrefix);
		
		
		initialize();
		checkState();
		return comp;
	}
	
	@Override
	protected void initialize() {
		super.initialize();
		IEclipsePreferences p = InstanceScope.INSTANCE.getNode(BpmnUIPlugin.PLUGIN_ID);
//		Preferences p = BpmnCorePlugin.getDefault().getPluginPreferences();
		
		String folder = p.get(BpmnCoreConstants.PREF_CODEGEN_FOLDER,BpmnCommonModelUtils.CODE_GEN_FOLDER_PATH);
		fFolder.setStringValue(folder);
		
		final String processPrefixStr = p.get(BpmnCoreConstants.PREF_CODEGEN_PROCESS_PREFIX,BpmnCommonModelUtils.BPMN_PROCESS_PREFIX);
		processPrefix.setStringValue(processPrefixStr);
		
		final String rulePrefixStr = p.get(BpmnCoreConstants.PREF_CODEGEN_RULE_PREFIX,BpmnCommonModelUtils.BPMN_RULE_PREFIX);
		rulePrefix.setStringValue(rulePrefixStr);
		
		final String ruleFnPrefixStr = p.get(BpmnCoreConstants.PREF_CODEGEN_RULE_FUNCTION_PREFIX,BpmnCommonModelUtils.BPMN_RULE_FUNCTION_PREFIX);
		rulefunctionPrefix.setStringValue(ruleFnPrefixStr);
		
		final String conceptPrefixStr = p.get(BpmnCoreConstants.PREF_CODEGEN_CONCEPT_PREFIX,BpmnCommonModelUtils.BPMN_CONCEPT_PREFIX);
		conceptPrefix.setStringValue(conceptPrefixStr);
		
		final String eventPrefixStr = p.get(BpmnCoreConstants.PREF_CODEGEN_EVENT_PREFIX,BpmnCommonModelUtils.BPMN_EVENT_PREFIX);
		eventPrefix.setStringValue(eventPrefixStr);
		
		final String timeeventPrefixStr = p.get(BpmnCoreConstants.PREF_CODEGEN_TIME_EVENT_PREFIX,BpmnCommonModelUtils.BPMN_TIME_EVENT_PREFIX);
		timeeventPrefix.setStringValue(timeeventPrefixStr);
		
		final String scorecardPrefixStr = p.get(BpmnCoreConstants.PREF_CODEGEN_SCORECARD_PREFIX,BpmnCommonModelUtils.BPMN_SCORECARD_PREFIX);
		scoreCardPrefix.setStringValue(scorecardPrefixStr);
	}
	
	
	@Override
	protected void performDefaults() {
//		Preferences p = BpmnCorePlugin.getDefault().getPluginPreferences();
		IEclipsePreferences p = InstanceScope.INSTANCE.getNode(BpmnUIPlugin.PLUGIN_ID);
		String folder = p.get(BpmnCoreConstants.PREF_CODEGEN_FOLDER,BpmnCommonModelUtils.CODE_GEN_FOLDER_PATH);
		fFolder.setStringValue(folder);
		
		final String processPrefixStr = p.get(BpmnCoreConstants.PREF_CODEGEN_PROCESS_PREFIX,BpmnCommonModelUtils.BPMN_PROCESS_PREFIX);
		processPrefix.setStringValue(processPrefixStr);
		
		final String rulePrefixStr = p.get(BpmnCoreConstants.PREF_CODEGEN_RULE_PREFIX,BpmnCommonModelUtils.BPMN_RULE_PREFIX);
		rulePrefix.setStringValue(rulePrefixStr);
		
		final String ruleFnPrefixStr = p.get(BpmnCoreConstants.PREF_CODEGEN_RULE_FUNCTION_PREFIX,BpmnCommonModelUtils.BPMN_RULE_FUNCTION_PREFIX);
		rulefunctionPrefix.setStringValue(ruleFnPrefixStr);
		
		final String conceptPrefixStr = p.get(BpmnCoreConstants.PREF_CODEGEN_CONCEPT_PREFIX,BpmnCommonModelUtils.BPMN_CONCEPT_PREFIX);
		conceptPrefix.setStringValue(conceptPrefixStr);
		
		final String eventPrefixStr = p.get(BpmnCoreConstants.PREF_CODEGEN_EVENT_PREFIX,BpmnCommonModelUtils.BPMN_EVENT_PREFIX);
		eventPrefix.setStringValue(eventPrefixStr);
		
		final String timeeventPrefixStr = p.get(BpmnCoreConstants.PREF_CODEGEN_TIME_EVENT_PREFIX,BpmnCommonModelUtils.BPMN_TIME_EVENT_PREFIX);
		timeeventPrefix.setStringValue(timeeventPrefixStr);
		
		final String scorecardPrefixStr = p.get(BpmnCoreConstants.PREF_CODEGEN_SCORECARD_PREFIX,BpmnCommonModelUtils.BPMN_SCORECARD_PREFIX);
		scoreCardPrefix.setStringValue(scorecardPrefixStr);
		
		super.performDefaults();
	}

	@Override
	public void init(IWorkbench workbench) {
		// TODO Auto-generated method stub
//		Preferences p = BpmnCorePlugin.getDefault().getPluginPreferences();
		IEclipsePreferences p = InstanceScope.INSTANCE.getNode(BpmnUIPlugin.PLUGIN_ID);
		@SuppressWarnings("unused")
		String folder = p.get(BpmnCoreConstants.PREF_CODEGEN_FOLDER,BpmnCommonModelUtils.CODE_GEN_FOLDER_PATH);
	}
	
	public static Composite createComposite(Composite parent, int columns, int hspan, int fill) {
		Composite g = new Composite(parent, SWT.NONE);
    	g.setLayout(new GridLayout(columns, false));
    	g.setFont(parent.getFont());
    	GridData gd = new GridData(fill);
		gd.horizontalSpan = hspan;
    	g.setLayoutData(gd);
    	return g;
	}
	
	/**
	 * Creates and returns a new push button with the given
	 * label and/or image.
	 * 
	 * @param parent parent control
	 * @param label button label or <code>null</code>
	 * @param image image of <code>null</code>
	 * 
	 * @return a new push button
	 */
	public static Button createPushButton(Composite parent, String label, Image image) {
		Button button = new Button(parent, SWT.PUSH);
		button.setFont(parent.getFont());
		if (image != null) {
			button.setImage(image);
		}
		if (label != null) {
			button.setText(label);
		}
		GridData gd = new GridData();
		button.setLayoutData(gd);	
		setButtonDimensionHint(button);
		return button;	
	}	
	
	/**
	 * Returns a width hint for a button control.
	 */
	public static int getButtonWidthHint(Button button) {
		button.setFont(JFaceResources.getDialogFont());
		PixelConverter converter= new PixelConverter(button);
		int widthHint= converter.convertHorizontalDLUsToPixels(IDialogConstants.BUTTON_WIDTH);
		return Math.max(widthHint, button.computeSize(SWT.DEFAULT, SWT.DEFAULT, true).x);
	}
	
	/**
	 * Sets width and height hint for the button control.
	 * <b>Note:</b> This is a NOP if the button's layout data is not
	 * an instance of <code>GridData</code>.
	 * 
	 * @param	the button for which to set the dimension hint
	 */		
	public static void setButtonDimensionHint(Button button) {
		Assert.isNotNull(button);
		Object gd= button.getLayoutData();
		if (gd instanceof GridData) {
			((GridData)gd).widthHint= getButtonWidthHint(button);	
			((GridData)gd).horizontalAlignment = GridData.FILL;	 
		}
	}	

	/**
	 * Creates and returns a new push button with the given
	 * label and/or image.
	 * 
	 * @param parent parent control
	 * @param label button label or <code>null</code>
	 * @param image image of <code>null</code>
	 * @param fill the alignment for the new button
	 * 
	 * @return a new push button
	 * @since 3.4
	 */
	public static Button createPushButton(Composite parent, String label, Image image, int fill) {
		Button button = new Button(parent, SWT.PUSH);
		button.setFont(parent.getFont());
		if (image != null) {
			button.setImage(image);
		}
		if (label != null) {
			button.setText(label);
		}
		GridData gd = new GridData(fill);
		button.setLayoutData(gd);	
		setButtonDimensionHint(button);
		return button;	
	}
	
	
public static class PixelConverter {
		
		private FontMetrics fFontMetrics;
		
		public PixelConverter(Control control) {
			GC gc = new GC(control);
			gc.setFont(control.getFont());
			fFontMetrics= gc.getFontMetrics();
			gc.dispose();
		}
			
		/**
		 * @see DialogPage#convertHorizontalDLUsToPixels
		 */
		public int convertHorizontalDLUsToPixels(int dlus) {
			return Dialog.convertHorizontalDLUsToPixels(fFontMetrics, dlus);
		}
		
		/**
		 * @see DialogPage#convertWidthInCharsToPixels
		 */
		public int convertWidthInCharsToPixels(int chars) {
			return Dialog.convertWidthInCharsToPixels(fFontMetrics, chars);
		}	
	}

}
