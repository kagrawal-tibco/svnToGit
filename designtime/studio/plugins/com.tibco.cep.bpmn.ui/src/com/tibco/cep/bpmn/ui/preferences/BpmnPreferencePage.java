package com.tibco.cep.bpmn.ui.preferences;

import static com.tibco.cep.bpmn.ui.utils.BpmnPaletteResourceUtil.refreshPalette;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.bpmn.ui.Messages;
import com.tibco.cep.bpmn.ui.properties.BpmnBuildPropertyPage;

/**
 * @author pdhar
 *
 */
public class BpmnPreferencePage extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage, BpmnPreferenceConstants {

	public BpmnPreferencePage() {
		super(FieldEditorPreferencePage.GRID);
		setPreferenceStore(BpmnUIPlugin.getDefault().getPreferenceStore());
	}

	@Override
	protected void createFieldEditors() {
		Composite parent = getFieldEditorParent();
		addField(new BooleanFieldEditor(BpmnPreferenceConstants.PREF_SHOW_TASK_ICONS,	
				Messages.getString("BPMN_GRAPH_SHOW_TASK_ICONS"), parent));
		addField(new BooleanFieldEditor(BpmnPreferenceConstants.PREF_FILL_TASK_NODES,	
				Messages.getString("BPMN_GRAPH_FILL_TASK_NODES"), parent));
//		addField(new BooleanFieldEditor(BpmnPreferenceConstants.PREF_EXPAND_SUBPROCESS,	
//				Messages.getString("BPMN_GRAPH_EXPAND_SUBPROCESS"), parent));
//		addField(new BooleanFieldEditor(BpmnPreferenceConstants.PREF_DISPLAY_FULL_NAMES,	
//				Messages.getString("BPMN_GRAPH_DISPLAY_FULL_NAMES"), parent));
		addField(new BooleanFieldEditor(BpmnPreferenceConstants.PREF_SHOW_SEQUENCE_LABEL,	
				Messages.getString("BPMN_GRAPH_SHOW_SEQUENCE_LABELS"), parent));
		
		String[][] labelAndValues = {
				{ Messages.getString("NONE") , BpmnPreferenceConstants.NONE},
				{ Messages.getString("LINE") , BpmnPreferenceConstants.LINE },
				{ Messages.getString("POINT") , BpmnPreferenceConstants.POINT } };
		
		RadioGroupFieldEditor gridFieldEditor = new RadioGroupFieldEditor(BpmnPreferenceConstants.GRID,
				Messages.getString("GRID"), 6, labelAndValues, getFieldEditorParent(), true);
		addField(gridFieldEditor);

//		addField(new BooleanFieldEditor(BpmnPreferenceConstants.PREF_ALIGN_TO_LEFT_TRIGGERING_NODES,	
//				Messages.getString("ALIGN_TO_LEFT_TRIGGERING_NODES"), parent));
		
		addField(new BooleanFieldEditor(BpmnPreferenceConstants.PREF_SHOW_BREAKPOINTS,	
				Messages.getString("BPMN_SHOW_BREAKPOINTS"), parent));
		addField(new BooleanFieldEditor(BpmnPreferenceConstants.PREF_SHOW_BREAKPOINTS_ON_MOUSEOVER,	
				Messages.getString("BPMN_SHOW_BREAKPOINTS_ON_MOUSEOVER"), parent));
		addField(new BooleanFieldEditor(BpmnPreferenceConstants.PREF_ALIGN_TO_LEFT_TRIGGERING_NODES,	
				Messages.getString("ALIGN_TO_LEFT_TRIGGERING_NODES"), parent));
		/*addField(new ComboFieldEditor(BpmnPreferenceConstants.PREF_SHOW_IMAGE_PIXELS,
				Messages.getString("BPMN_IMAGE_PIXELS"),null,parent));*/
		
		String[][] labelAndValuesForImagePixels = {
				{ "16x16" , BpmnPreferenceConstants.PREF_SHOW_IMAGE_PIXELS_16},
				{ "24x24" , BpmnPreferenceConstants.PREF_SHOW_IMAGE_PIXELS_24},
				{ "32x32" , BpmnPreferenceConstants.PREF_SHOW_IMAGE_PIXELS_32},
				{ "48x48" , BpmnPreferenceConstants.PREF_SHOW_IMAGE_PIXELS_48} 
			};
		
		RadioGroupFieldEditor gridFieldEditorforImagePixels = new RadioGroupFieldEditor(BpmnPreferenceConstants.PREF_SHOW_IMAGE_PIXELS,
				Messages.getString("BPMN_IMAGE_PIXELS"), 6, labelAndValuesForImagePixels, getFieldEditorParent(), true);
		addField(gridFieldEditorforImagePixels);
		
		addField(new BooleanFieldEditor(BpmnPreferenceConstants.PREF_CHANGE_PALLETE_IMAGE_PIXELS,	
				Messages.getString("BPMN_CHANGE_PALLETE_IMAGE_PIXELS"), parent));
	}

	@Override
	public void init(IWorkbench workbench) {
		// TODO Auto-generated method stub

	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.preference.FieldEditorPreferencePage#performOk()
	 */
	@Override
	public boolean performOk() {
		boolean status = super.performOk();
		refreshPalette();
		return status;
	}

}
