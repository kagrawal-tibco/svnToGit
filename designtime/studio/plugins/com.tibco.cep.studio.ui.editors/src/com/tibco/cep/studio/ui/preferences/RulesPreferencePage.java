package com.tibco.cep.studio.ui.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;
import com.tibco.cep.studio.ui.editors.utils.Messages;

/**
 * 
 * @author sasahoo
 *
 */
public class RulesPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage,IPreferences {
	
	public RulesPreferencePage(){
		super(GRID);
		setPreferenceStore(EditorsUIPlugin.getDefault().getPreferenceStore());
	}

	@Override
	protected void createFieldEditors() {
		Composite parent = getFieldEditorParent();
		Group group = new Group(parent, SWT.NULL);
		group.setText("Editor");
		group.setLayout(new GridLayout());
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		addField(new BooleanFieldEditor(StudioPreferenceConstants.RULE_FORM_INTIAL_PAGE,
				Messages.getString("studio.rule.form.as.intial.page"),group));
		addField(new BooleanFieldEditor(StudioPreferenceConstants.RULE_FUNCTION_FORM_INTIAL_PAGE,
				Messages.getString("studio.rule.function.form.as.intial.page"),group));
		addField(new BooleanFieldEditor(StudioPreferenceConstants.RULE_TEMPLATE_FORM_INTIAL_PAGE,
				Messages.getString("studio.rule.template.form.as.intial.page"),group));
		addField(new BooleanFieldEditor(StudioPreferenceConstants.RULE_EDITOR_SHOW_TOOLTIPS,
				Messages.getString("studio.rule.tooltips.show"),group));
		
	}
	
	public void init(IWorkbench workbench) {
	}
	
}