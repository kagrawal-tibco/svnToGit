package com.tibco.cep.studio.cluster.topology.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.tibco.cep.studio.cluster.topology.ClusterTopologyPlugin;
import com.tibco.cep.studio.cluster.topology.utils.Messages;

/**
 * 
 * @author ggrigore
 *
 */
public class ClusterTopologyPreferencePage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	public static final String Id = "com.tibco.cep.studio.cluster.topology.preferencePage";	

	public ClusterTopologyPreferencePage() {
		super(GRID);
		setPreferenceStore(ClusterTopologyPlugin.getDefault()
				.getPreferenceStore());
	}

	@Override
	protected void createFieldEditors() {
		String[][] labelAndValues = {
				{ Messages.getString("NONE"), PreferenceConstants.NONE},
				{ Messages.getString("LINE"), PreferenceConstants.LINE },
				{ Messages.getString("POINT"), PreferenceConstants.POINT } };
		RadioGroupFieldEditor gridFieldEditor = new RadioGroupFieldEditor(PreferenceConstants.GRID,
				Messages.getString("GRID"), 6, labelAndValues, getFieldEditorParent());
		addField(gridFieldEditor);
	}

	public void init(IWorkbench workbench) {
	}

}
