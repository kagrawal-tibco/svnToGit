package com.tibco.cep.studio.decision.table.preferences;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;

import com.tibco.cep.studio.decision.table.configuration.DTStyleConstants;
import com.tibco.cep.studio.decision.table.ui.DecisionTableUIPlugin;





public class DTPreferenceInitializer extends AbstractPreferenceInitializer {

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	@Override
	public void initializeDefaultPreferences() {
		
		IPreferenceStore prefStore = DecisionTableUIPlugin.getDefault().getPreferenceStore();
		
			
		prefStore.setDefault(PreferenceConstants.AUTO_RESIZE_COLUMN, true);
		prefStore.setDefault(PreferenceConstants.AUTO_RESIZE_ROWS, true);
		prefStore.setDefault(PreferenceConstants.AUTO_MERGE_VIEW, false);
		prefStore.setDefault(PreferenceConstants.EXISTING_ID_IMPORT , true);
		prefStore.setDefault(PreferenceConstants.VALIDATE_DURING_COMMIT , true);
		prefStore.setDefault(PreferenceConstants.SHOW_RULES, true);
		prefStore.setDefault(PreferenceConstants.SHOW_ALIAS, false);
		prefStore.setDefault(PreferenceConstants.SHOW_COLUMN_ALIAS, false);
		prefStore.setDefault(PreferenceConstants.SHOW_COLUMN_GROUPS, true);
		prefStore.setDefault(PreferenceConstants.SHOW_ACTION_AREA_STRING, true);
		prefStore.setDefault(PreferenceConstants.SHOW_VER_COND_AREA_STRING, true);
		prefStore.setDefault(PreferenceConstants.SHOW_HORIZONTAL_COND_AREA_STRING, true);
		prefStore.setDefault(PreferenceConstants.SHOW_COLUMN_ID, false);
		prefStore.setDefault(PreferenceConstants.SHOW_COLUMN_FILTER, true);
		
		prefStore.setDefault(PreferenceConstants.MENU_OPTION, PreferenceConstants.SHOW_ROLL_OVER_TOOL_BAR);
		
		{
			prefStore.setDefault(PreferenceConstants.DISABLED_DATA_BACK_GROUND_COLOR, "215,215,215");
			prefStore.setDefault(PreferenceConstants.DISABLED_DATA_FORE_GROUND_COLOR, "125,125,125");
			
			// the colors are handled via DTStyleConstants
			DTStyleConstants.setDefaultDTColorPalette(DTStyleConstants.STANDARD_PALETTE);
			prefStore.setDefault(PreferenceConstants.SELECTED_COLOR_PALETTE, "Standard");
		}
		
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
		prefStore.setDefault(PreferenceConstants.COMPARE_SHOW_INFO_PROP,true);

		prefStore.setDefault(PreferenceConstants.CONDITION_FIELD_FILTER, PreferenceConstants.FILTER_BUTTON_ON);
		prefStore.setDefault(PreferenceConstants.ACTION_FIELD_FILTER, PreferenceConstants.FILTER_BUTTON_OFF);
		prefStore.setDefault(PreferenceConstants.ALTERNATE_ROW_COLORS, true);
		prefStore.setDefault(PreferenceConstants.USE_GRADIENTS, false);
		
		if (Platform.getOS().equals(Platform.OS_MACOSX)) {
			prefStore.setDefault(PreferenceConstants.COLUMN_HEADER_FONT, JFaceResources.getBannerFont().getFontData()[0].toString());
			prefStore.setDefault(PreferenceConstants.COND_DATA_FONT, JFaceResources.getDefaultFont().getFontData()[0].toString());
			prefStore.setDefault(PreferenceConstants.ACTION_DATA_FONT, JFaceResources.getDefaultFont().getFontData()[0].toString());
//			prefStore.setDefault(PreferenceConstants.COLUMN_HEADER_FONT, new FontData("Arial", 14, SWT.NORMAL).toString());
//			prefStore.setDefault(PreferenceConstants.COND_DATA_FONT, new FontData("Arial", 13, SWT.NORMAL).toString());
//			prefStore.setDefault(PreferenceConstants.ACTION_DATA_FONT,new FontData("Arial", 13, SWT.NORMAL).toString());
		} else {
			prefStore.setDefault(PreferenceConstants.COLUMN_HEADER_FONT, JFaceResources.getBannerFont().getFontData()[0].toString());
			prefStore.setDefault(PreferenceConstants.COND_DATA_FONT, JFaceResources.getDefaultFont().getFontData()[0].toString());
			prefStore.setDefault(PreferenceConstants.ACTION_DATA_FONT, JFaceResources.getDefaultFont().getFontData()[0].toString());
//			prefStore.setDefault(PreferenceConstants.COLUMN_HEADER_FONT, new FontData(DTStyleConstants.FONT_NAME, 11, SWT.NORMAL).toString());
//			prefStore.setDefault(PreferenceConstants.COND_DATA_FONT, new FontData(DTStyleConstants.FONT_NAME, 10, SWT.NORMAL).toString());
//			prefStore.setDefault(PreferenceConstants.ACTION_DATA_FONT,new FontData(DTStyleConstants.FONT_NAME, 10, SWT.NORMAL).toString());
		}
		
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
		
		prefStore.setDefault(PreferenceConstants.DT_DOMAIN_IS_CELL_EDITABLE, true);
		
		prefStore.setDefault(PreferenceConstants.DECISION_MANAGER_KEEP_LAST_ACTIVE_EDITORS, false);
		prefStore.setDefault(PreferenceConstants.DECISION_MANAGER_OPENING_LAST_ACTIVE_PROJECT, false);
		
		prefStore.setDefault(PreferenceConstants.LINK_WITH_EDITOR, false);
		
		prefStore.setDefault(PreferenceConstants.ALWAYS_MAINTAIN_RULE_IDS, true);
		prefStore.setDefault(PreferenceConstants.REUSE_DELETED_RULE_IDS, true);
	}

}
