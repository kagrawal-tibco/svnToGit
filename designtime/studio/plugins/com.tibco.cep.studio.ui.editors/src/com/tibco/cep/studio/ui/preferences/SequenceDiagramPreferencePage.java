package com.tibco.cep.studio.ui.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;
import com.tibco.cep.studio.ui.editors.utils.Messages;

/**
 * 
 * @author sasahoo
 *
 */
public class SequenceDiagramPreferencePage extends FieldEditorPreferencePage
	implements IWorkbenchPreferencePage,IPreferences {
	
	public SequenceDiagramPreferencePage(){
		super(GRID);
		setPreferenceStore(EditorsUIPlugin.getDefault().getPreferenceStore());
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.preference.FieldEditorPreferencePage#createFieldEditors()
	 */
	@Override
	protected void createFieldEditors() {
		Composite parent = getFieldEditorParent();
		addField(new BooleanFieldEditor(StudioPreferenceConstants.SEQUENCE_SHOW_CATALOG_FUNCTIONS,	
				Messages.getString("studio.sequence.diagram.showCatalogFunctions"),parent));
		addField(new BooleanFieldEditor(StudioPreferenceConstants.SEQUENCE_SHOW_EXPANDED_NAMES,	
				Messages.getString("studio.sequence.diagram.showExpandedNames"),parent));
		addField(new BooleanFieldEditor(StudioPreferenceConstants.SEQUENCE_SHOW_CATFUNC_RETURNLINKS,	
				Messages.getString("studio.sequence.diagram.showCatalogFuncReturnLinks"),parent));
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}
}
