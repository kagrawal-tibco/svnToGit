package com.tibco.cep.diagramming.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import com.tibco.cep.diagramming.DiagrammingPlugin;


/**
 * 
 * @author sasahoo
 *
 */
public class DiagramPreferenceInitializer extends AbstractPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore prefStore = DiagrammingPlugin.getDefault().getPreferenceStore();

		//Diagram
		prefStore.setDefault(DiagramPreferenceConstants.RESET_TOOL_AFTER_CHANGES, true);
		prefStore.setDefault(DiagramPreferenceConstants.AUTO_HIDE_SCROLL_BARS, true);
		prefStore.setDefault(DiagramPreferenceConstants.SHOW_TOOLTIPS, true);
		prefStore.setDefault(DiagramPreferenceConstants.PREF_FILL_SUBPROCESS_NODES, false);
		prefStore.setDefault(DiagramPreferenceConstants.UNDO_LIMIT, true);
		prefStore.setDefault(DiagramPreferenceConstants.OPAQUE_MOVEMENT, true);
		prefStore.setDefault(DiagramPreferenceConstants.INTERACTIVE_ZOOM_SENSITIVITY, 200.0);
		prefStore.setDefault(DiagramPreferenceConstants.PAN_SENSITIVITY, 1.0);
		prefStore.setDefault(DiagramPreferenceConstants.MAGNIFY_SIZE, 250);
		prefStore.setDefault(DiagramPreferenceConstants.MAGNIFY_ZOOM, 3);
		prefStore.setDefault(DiagramPreferenceConstants.RUN_LAYOUT_ON_CHANGES, DiagramPreferenceConstants.NONE);
		
		prefStore.setDefault(DiagramPreferenceConstants.LINK_TYPE, DiagramPreferenceConstants.LINK_TYPE_STRAIGHT);

		prefStore.setDefault(DiagramPreferenceConstants.ANIMATION_LAYOUT_ALLOW, true);
		prefStore.setDefault(DiagramPreferenceConstants.ANIMATION_LAYOUT_FADE,true);
		prefStore.setDefault(DiagramPreferenceConstants.ANIMATION_LAYOUT_INTERPOLATION,true);
		prefStore.setDefault(DiagramPreferenceConstants.ANIMATION_LAYOUT_VIEWPORT_CHANGE,true);
		prefStore.setDefault(DiagramPreferenceConstants.LAYOUT_ANIMATION_DURATION,500);
		prefStore.setDefault(DiagramPreferenceConstants.VIEWPORT_CHANGE_DURATION, 1000);
		}

}
