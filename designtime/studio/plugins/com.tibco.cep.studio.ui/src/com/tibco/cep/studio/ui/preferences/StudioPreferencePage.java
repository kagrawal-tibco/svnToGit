package com.tibco.cep.studio.ui.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
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
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.util.Messages;

/**
 * 
 * @author sasahoo
 *
 */
public class StudioPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {


	public StudioPreferencePage() {
		super(GRID);
		setPreferenceStore(StudioUIPlugin.getDefault().getPreferenceStore());
	}

	public void init(IWorkbench workbench) {
	}

	@Override
	protected void createFieldEditors() {
		Composite parent = getFieldEditorParent();
		parent.setLayout(new GridLayout());
		parent.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		addField(new BooleanFieldEditor(StudioUIPreferenceConstants.CATALOG_FUNCTION_SHOW_TOOLTIPS,
				Messages.getString("studio.catalogFunctions.tooltips.show"),parent));
		addField(new BooleanFieldEditor(StudioUIPreferenceConstants.OPEN_CONFIRM_PERSPECTIVE_CHANGE,
				Messages.getString("open.show.perspective.show.preference"),parent));
		addField(new BooleanFieldEditor(StudioUIPreferenceConstants.SWITCH_PERSPECTIVE_ON_EDITOR_ACTIVATION,
				Messages.getString("open.default.perspective.on.editor.activation"),parent));
		addField(new BooleanFieldEditor(StudioUIPreferenceConstants.STUDIO_OVERWRITE_EAR,
				Messages.getString("overwrite.existing.ear"),parent));
		addField(new BooleanFieldEditor(StudioUIPreferenceConstants.STUDIO_PERSIST_ENTITIES_AS_SOURCE,
				Messages.getString("persist.entities.as.source"),parent));
	}

	@Override
	protected Control createContents(Composite parent) {
		Composite control = (Composite) super.createContents(parent);
		Group dialogGroup = new Group(control, SWT.NULL);
		dialogGroup.setLayout(new GridLayout(2, false));
		dialogGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		dialogGroup.setText("Dialogs");
		Label l = new Label(dialogGroup, SWT.NULL);
		l.setText(Messages.getString("clear.all.do.not.show.dialogs"));
		Button b = new Button(dialogGroup, SWT.NULL);
		b.setText("Clear");
		b.addSelectionListener(new SelectionListener() {
		
			@Override
			public void widgetSelected(SelectionEvent e) {
				String[] doNotShowPrefs = StudioUIPreferenceConstants.DO_NOT_SHOW_PREFS;
				for (String pref : doNotShowPrefs) {
					if (getPreferenceStore().getBoolean(pref)) {
						getPreferenceStore().setValue(pref, false);
					}
				}
			}
		
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		return control;
	}
	
}