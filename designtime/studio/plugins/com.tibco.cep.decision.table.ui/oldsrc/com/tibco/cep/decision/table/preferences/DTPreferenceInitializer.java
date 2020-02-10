package com.tibco.cep.decision.table.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;

import com.tibco.cep.decision.table.ui.DecisionTableUIPlugin;



public class DTPreferenceInitializer extends AbstractPreferenceInitializer {

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore prefStore = DecisionTableUIPlugin.getDefault().getPreferenceStore();
		
		/*IPreferenceStore decisionProjectPrefStore = Activator.getDefault().getPreferenceStore();
		decisionProjectPrefStore.setDefault(DecisionManagerServerPreferenceConstants.DECISION_MANAGER_SERVER_AUTH_URLS_SIZE, 4);
		decisionProjectPrefStore.setDefault(DecisionManagerServerPreferenceConstants.DECISION_MANAGER_SERVER_CHECKOUT_URLS_SIZE, 4);*/
		
//		IPreferenceStore msprefStore = MgmtServerPlugin.getDefault().getPreferenceStore();
//		msprefStore.setDefault(ManagementServerPreferenceConstants.DECISION_MANAGER_FETCH_DOMAIN_FROM_DB_CONCEPT, false);
		
		prefStore.setDefault(PreferenceConstants.AUTO_RESIZE_COLUMN, true);
		prefStore.setDefault(PreferenceConstants.AUTO_MERGE_VIEW, false);
		prefStore.setDefault(PreferenceConstants.EXISTING_ID_IMPORT , true);
		prefStore.setDefault(PreferenceConstants.VALIDATE_DURING_COMMIT , true);
		prefStore.setDefault(PreferenceConstants.SHOW_RULES, true);
		prefStore.setDefault(PreferenceConstants.SHOW_ALIAS, false);
		prefStore.setDefault(PreferenceConstants.SHOW_TITLED_BORDER, true);
		prefStore.setDefault(PreferenceConstants.SHOW_ACTION_AREA_STRING, true);
		prefStore.setDefault(PreferenceConstants.SHOW_VER_COND_AREA_STRING, true);
		prefStore.setDefault(PreferenceConstants.SHOW_HORIZONTAL_COND_AREA_STRING, true);
		prefStore.setDefault(PreferenceConstants.SHOW_COLUMN_ID, false);
		
		prefStore.setDefault(PreferenceConstants.MENU_OPTION, PreferenceConstants.SHOW_ROLL_OVER_TOOL_BAR);
		prefStore.setDefault(PreferenceConstants.HEADER_BACK_GROUND_COLOR, "255,255,233");
		prefStore.setDefault(PreferenceConstants.HEADER_FORE_GROUND_COLOR, "0,0,0");
		prefStore.setDefault(PreferenceConstants.ACTION_HEADER_BACK_GROUND_COLOR, "244,244,244");
		prefStore.setDefault(PreferenceConstants.ACTION_HEADER_FORE_GROUND_COLOR,"0,0,0");	
		prefStore.setDefault(PreferenceConstants.ACTION_DATA_BACK_GROUND_COLOR,"255,255,255");
		prefStore.setDefault(PreferenceConstants.ACTION_DATA_FORE_GROUND_COLOR,"0,0,0");
		prefStore.setDefault(PreferenceConstants.VER_COND_DATA_BACK_GROUND_COLOR,"255,255,215");
		prefStore.setDefault(PreferenceConstants.VER_COND_DATA_FORE_GROUND_COLOR,"0,0,0");
		
		prefStore.setDefault(PreferenceConstants.COMPARE_MERGE_AUTOMERGE_COLUMNS,true);
		prefStore.setDefault(PreferenceConstants.COMPARE_ADDED_COLOR,"228,228,241");
		prefStore.setDefault(PreferenceConstants.COMPARE_REMOVED_COLOR,"254,218,235");
		prefStore.setDefault(PreferenceConstants.COMPARE_CHANGED_COLOR,"255,255,255");
		prefStore.setDefault(PreferenceConstants.COMPARE_SINGLE_LINE_PROP,false);
		prefStore.setDefault(PreferenceConstants.COMPARE_OPEN_STRUCTURE_PROP,true);
		prefStore.setDefault(PreferenceConstants.COMPARE_OPEN_TEXT_PROP,true);
		prefStore.setDefault(PreferenceConstants.COMPARE_SYNC_SCROLL_PROP,true);
		prefStore.setDefault(PreferenceConstants.COMPARE_HIGHLIGHT_CHANGES_PROP,true);
		prefStore.setDefault(PreferenceConstants.COMPARE_IGNORE_WHITESPACE_PROP,true);
//		prefStore.setDefault(PreferenceConstants.COMPARE_SAVE_EDITORS_PROP,true);
		prefStore.setDefault(PreferenceConstants.COMPARE_SHOW_INFO_PROP,true);

		prefStore.setDefault(PreferenceConstants.CONDITION_FIELD_FILTER, PreferenceConstants.FILTER_BUTTON_ON);
		prefStore.setDefault(PreferenceConstants.ACTION_FIELD_FILTER, PreferenceConstants.FILTER_BUTTON_OFF);
		
		String defaultFont = "1|Tahoma|11.0|1|WINDOWS|1|0|0|0|0|400|0|0|0|1|0|0|0|0|Tahoma";
		prefStore.setDefault(PreferenceConstants.COND_DATA_FONT, defaultFont);
		prefStore.setDefault(PreferenceConstants.ACTION_DATA_FONT, defaultFont);
		
		prefStore.setDefault(PreferenceConstants.EDITOR_SECTIONS, false);
		prefStore.setDefault(PreferenceConstants.HORIZONTAL_VERTICAL_DECISION_VIEW, false);
		
		prefStore.setDefault(PreferenceConstants.CONSOLE_COLOR_DEBUG, "0,0,255");
		prefStore.setDefault(PreferenceConstants.CONSOLE_COLOR_INFO,  "92,0,0");
		prefStore.setDefault(PreferenceConstants.CONSOLE_COLOR_WARN,  "0,200,200");
		prefStore.setDefault(PreferenceConstants.CONSOLE_COLOR_ERROR, "255,0,0");
		prefStore.setDefault(PreferenceConstants.CONSOLE_COLOR_FATAL, "255,0,0");
		prefStore.setDefault(PreferenceConstants.CONSOLE_COLOR_STDOUT,"200,200,200");
		prefStore.setDefault(PreferenceConstants.CONSOLE_COLOR_STDERR, "200,200,200");
		prefStore.setDefault(PreferenceConstants.ANALYSER_BACK_GROUND_COLOR, "119,145,229");
		prefStore.setDefault(PreferenceConstants.CONSOLE_STYLE_DEBUG, 0);
		prefStore.setDefault(PreferenceConstants.CONSOLE_STYLE_INFO,  0);
		prefStore.setDefault(PreferenceConstants.CONSOLE_STYLE_WARN,  0);
		prefStore.setDefault(PreferenceConstants.CONSOLE_STYLE_ERROR, SWT.BOLD);
		prefStore.setDefault(PreferenceConstants.CONSOLE_STYLE_FATAL, 0);
		prefStore.setDefault(PreferenceConstants.CONSOLE_STYLE_STDOUT,SWT.ITALIC);
		prefStore.setDefault(PreferenceConstants.CONSOLE_STYLE_STDERR,SWT.ITALIC|SWT.BOLD);

		prefStore.setDefault(PreferenceConstants.PROBLEMS_MAX_ITEMS, 		100);
		prefStore.setDefault(PreferenceConstants.PROBLEMS_SHOW_RESOURCE, 	true);
		prefStore.setDefault(PreferenceConstants.PROBLEMS_SORT_ORDER, 		"SEVERITY");
		prefStore.setDefault(PreferenceConstants.PROBLEMS_ACTIVATE_ON_PROB, true);
		
		prefStore.setDefault(PreferenceConstants.ANALYZER_SHOW_TABLE_ANALYZER_VIEW,	true);
		prefStore.setDefault(PreferenceConstants.ANALYZER_HIGHLIGHT_PARTIAL_RANGES,	false);
		prefStore.setDefault(PreferenceConstants.ANALYZER_INTELLIGENT_TEST_GEN,	true);
		prefStore.setDefault(PreferenceConstants.RUN_ANALYZER, true);
		
		prefStore.setDefault(PreferenceConstants.DECISION_MANAGER_KEEP_LAST_ACTIVE_EDITORS, false);
		prefStore.setDefault(PreferenceConstants.DECISION_MANAGER_OPENING_LAST_ACTIVE_PROJECT, false);
		
		prefStore.setDefault(PreferenceConstants.LINK_WITH_EDITOR, false);
//		prefStore.setDefault(PreferenceConstants.INTEGER_RANGE_VALUE, "5");
	}

}
