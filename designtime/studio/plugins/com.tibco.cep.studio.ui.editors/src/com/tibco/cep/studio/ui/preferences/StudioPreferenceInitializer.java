package com.tibco.cep.studio.ui.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import com.tibco.cep.diagramming.preferences.DiagramPreferenceConstants;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;
/**
 * 
 * @author sasahoo
 *
 */
public class StudioPreferenceInitializer extends AbstractPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore prefStore = EditorsUIPlugin.getDefault().getPreferenceStore();

		//Diagram
		prefStore.setDefault(DiagramPreferenceConstants.AUTO_HIDE_SCROLL_BARS, true);
		prefStore.setDefault(DiagramPreferenceConstants.SHOW_TOOLTIPS, true);
		prefStore.setDefault(DiagramPreferenceConstants.UNDO_LIMIT, true);
		prefStore.setDefault(DiagramPreferenceConstants.OPAQUE_MOVEMENT, true);
		prefStore.setDefault(DiagramPreferenceConstants.INTERACTIVE_ZOOM_SENSITIVITY, 200.0);
		prefStore.setDefault(DiagramPreferenceConstants.PAN_SENSITIVITY, 1.0);
		//TODO

		//Concept
		prefStore.setDefault(StudioPreferenceConstants.CONCEPT_GRID, StudioPreferenceConstants.CONCEPT_LINES);
		prefStore.setDefault(StudioPreferenceConstants.CONCEPT_SHOW_ALL_PROPERTIES_IN_CONCEPT_NODE, false);
		//TODO
		
		//Event
		prefStore.setDefault(StudioPreferenceConstants.EVENT_GRID, StudioPreferenceConstants.EVENT_LINES);
		prefStore.setDefault(StudioPreferenceConstants.EVENT_SHOW_ALL_PROPERTIES_IN_EVENT_NODE, false);
		
		// Dependency 
		prefStore.setDefault(StudioPreferenceConstants.DEPENDENCY_GRID, StudioPreferenceConstants.DEPENDENCY_LINES);
		prefStore.setDefault(StudioPreferenceConstants.DEPENDENCY_LEVELS, StudioPreferenceConstants.DEPENDENCY_ONE);
		
		//Project
		//TODO
		prefStore.setDefault(StudioPreferenceConstants.PROJECT_GRID, StudioPreferenceConstants.PROJECT_LINES);
		prefStore.setDefault(StudioPreferenceConstants.PROJECT_LEVELS, StudioPreferenceConstants.PROJECT_ONE);
		prefStore.setDefault(StudioPreferenceConstants.CREATE_VIEW, false);
		prefStore.setDefault(StudioPreferenceConstants.RUN_ANALYSIS, false);
		prefStore.setDefault(StudioPreferenceConstants.FAST_LAYOUT, true);
		prefStore.setDefault(StudioPreferenceConstants.PROJECT_SHOW_CONCEPTS, true);
		prefStore.setDefault(StudioPreferenceConstants.PROJECT_SHOW_METRICS, true);
		prefStore.setDefault(StudioPreferenceConstants.PROJECT_SHOW_EVENTS, true);
		prefStore.setDefault(StudioPreferenceConstants.PROJECT_SHOW_CHANNELS, true);
		prefStore.setDefault(StudioPreferenceConstants.PROJECT_SHOW_ARCHIVES, true);
		prefStore.setDefault(StudioPreferenceConstants.PROJECT_SHOW_DECISIONTABLES, true);
		prefStore.setDefault(StudioPreferenceConstants.PROJECT_SHOW_DOMAINMODEL, true);
		prefStore.setDefault(StudioPreferenceConstants.PROJECT_SHOW_PROCESSES, true);
		prefStore.setDefault(StudioPreferenceConstants.PROJECT_SHOW_RULES, true);
		prefStore.setDefault(StudioPreferenceConstants.PROJECT_SHOW_RULES_FUNCTIONS, true);
		prefStore.setDefault(StudioPreferenceConstants.PROJECT_SHOW_SCORECARDS, true);
		prefStore.setDefault(StudioPreferenceConstants.PROJECT_SHOW_STATEMACHIES, true);
		prefStore.setDefault(StudioPreferenceConstants.PROJECT_SHOW_TOOLTIPS, true);
		prefStore.setDefault(StudioPreferenceConstants.PROJECT_SHOW_SCOPE_LINKS, true);
		prefStore.setDefault(StudioPreferenceConstants.PROJECT_SHOW_USAGE_LINKS, true);
		prefStore.setDefault(StudioPreferenceConstants.PROJECT_SHOW_PROCESS_LINKS, true);
		prefStore.setDefault(StudioPreferenceConstants.PROJECT_SHOW_PROCESS_EXPANDED, true);
		
		prefStore.setDefault(StudioPreferenceConstants.PROJECT_SHOW_ARCHIVED_DESTINATIONS, true);
		prefStore.setDefault(StudioPreferenceConstants.PROJECT_SHOW_ARCHIVED_RULES, true);
		prefStore.setDefault(StudioPreferenceConstants.PROJECT_SHOW_RULES_FOLDERS, true);
		//TODO
		
		//Print Appearance
		prefStore.setDefault(StudioPreferenceConstants.PRINT_CAPTION_TEXT, "");
		prefStore.setDefault(StudioPreferenceConstants.PRINT_FONT, "1|Tahoma|11.0|1|WINDOWS|1|0|0|0|0|400|0|0|0|1|0|0|0|0|Tahoma");
		prefStore.setDefault(StudioPreferenceConstants.PRINT_COLOR, "255,255,233");
		
		//Sequence
		prefStore.setDefault(StudioPreferenceConstants.SEQUENCE_SHOW_CATALOG_FUNCTIONS, true);
		prefStore.setDefault(StudioPreferenceConstants.SEQUENCE_SHOW_EXPANDED_NAMES, true);
		
		// Rules
		prefStore.setDefault(StudioPreferenceConstants.RULE_EDITOR_SHOW_TOOLTIPS, true);
//		prefStore.setDefault(StudioPreferenceConstants.RULE_TEMPLATE_FORM_INTIAL_PAGE, true);
//		prefStore.setDefault(StudioPreferenceConstants.RULE_FORM_INTIAL_PAGE, true);
//		prefStore.setDefault(StudioPreferenceConstants.RULE_FUNCTION_FORM_INTIAL_PAGE, true);
	}

}
