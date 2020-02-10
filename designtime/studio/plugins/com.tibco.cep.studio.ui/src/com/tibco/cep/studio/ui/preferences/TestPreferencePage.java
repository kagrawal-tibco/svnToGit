package com.tibco.cep.studio.ui.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.util.Messages;

public class TestPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {
	
	private StringFieldEditor testDataInputDirFieldEditor;
	private StringFieldEditor testDataOutputDirFieldEditor;
	
	public TestPreferencePage(){
		super(GRID);
		setPreferenceStore(StudioUIPlugin.getDefault().getPreferenceStore());
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	@Override
	public void init(IWorkbench workbench) {
		// TODO Auto-generated method stub
		
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.preference.FieldEditorPreferencePage#createFieldEditors()
	 */
	@Override
	protected void createFieldEditors() {
		Composite parent = getFieldEditorParent();
		parent.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));

		//addField(new BooleanFieldEditor(StudioUIPreferenceConstants.AUTO_DEPLOY_BEFORE_TEST,	"Automatically deploy all tables before starting project tests", parent));
		
//		testDataInputDirFieldEditor = new StringFieldEditor(
//				StudioUIPreferenceConstants.TEST_DATA_INPUT_PATH, Messages.getString("pref.ui.tester.input.dir"), parent);
//		addField(testDataInputDirFieldEditor);
		testDataOutputDirFieldEditor = new StringFieldEditor(
				StudioUIPreferenceConstants.TEST_DATA_OUTPUT_PATH, Messages.getString("pref.ui.tester.output.dir"), parent);
		addField(testDataOutputDirFieldEditor);
		IntegerFieldEditor maxWMObjectsShowFieldEditor = new IntegerFieldEditor(
				StudioUIPreferenceConstants.WM_OBJCECTS_SHOW_MAX_NO, Messages.getString("pref.ui.tester.max.wm.objects"), parent);
		addField(maxWMObjectsShowFieldEditor);
		
		addField(new BooleanFieldEditor(StudioUIPreferenceConstants.AUTO_SCROLL_RESULT_TABLE, 
				Messages.getString("pref.ui.tester.auto.scroll.result.table"), BooleanFieldEditor.SEPARATE_LABEL, parent));
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.preference.FieldEditorPreferencePage#performDefaults()
	 */
	@Override
	protected void performDefaults() {
		super.performDefaults();
	}
}