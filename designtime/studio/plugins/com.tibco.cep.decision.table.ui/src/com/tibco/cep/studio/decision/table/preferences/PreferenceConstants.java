package com.tibco.cep.studio.decision.table.preferences;

import org.eclipse.swt.graphics.RGB;

//TODO add proper support for preference store
public class PreferenceConstants {
	
	public static final String COMPARE_ADDED_COLOR = "compareAddedColor";
	public static final String COMPARE_REMOVED_COLOR = "compareRemovedColor";
	public static final String COMPARE_CHANGED_COLOR = "compareChangedColor";
	public static final String COMPARE_OPEN_STRUCTURE_PROP = "org.eclipse.compare.OpenStructureCompare";
	public static final String COMPARE_OPEN_TEXT_PROP = "org.eclipse.compare.OpenTextCompare";
	public static final String COMPARE_SYNC_SCROLL_PROP = "org.eclipse.compare.SynchronizeScrolling";
	public static final String COMPARE_SINGLE_LINE_PROP = "org.eclipse.compare.UseSingleLine";
	public static final String COMPARE_HIGHLIGHT_CHANGES_PROP = "org.eclipse.compare.HighlightTokenChanges";
	public static final String COMPARE_IGNORE_WHITESPACE_PROP = "org.eclipse.compare.IgnoreWhitespace";
	public static final String COMPARE_SAVE_EDITORS_PROP = "org.eclipse.compare.SaveAllEditors";
	public static final String COMPARE_SHOW_INFO_PROP = "org.eclipse.compare.ShowMoreInfo";
	
	public static final String COMPARE_MERGE_AUTOMERGE_COLUMNS = "org.eclipse.compare.merge.AutoMergeColumns";
	
	public static final String APPEARANCE_PREFIX = "decision.table.appearance.";
	public static final String ANALYSER_BACK_GROUND_COLOR = APPEARANCE_PREFIX+"analyserBackGroundColor";
	public static final String HEADER_GROUP_BACK_GROUND_COLOR = APPEARANCE_PREFIX+"headerGroupBackGroundColor";
	public static final String HEADER_GROUP_FORE_GROUND_COLOR = APPEARANCE_PREFIX+"headerGroupForeGroundColor";
	public static final String HEADER_BACK_GROUND_COLOR = APPEARANCE_PREFIX+"headerBackGroundColor";
	public static final String HEADER_FORE_GROUND_COLOR = APPEARANCE_PREFIX+"headerForeGroundColor";
	public static final String ACTION_HEADER_BACK_GROUND_COLOR = APPEARANCE_PREFIX+"HeaderGradientBackGroundColor";
	public static final String ACTION_HEADER_FORE_GROUND_COLOR = APPEARANCE_PREFIX+"actionHeaderForeGroundColor";
	public static final String COND_DATA_BACK_GROUND_COLOR = APPEARANCE_PREFIX+"CondDataBackGroundColor";
	public static final String COND_DATA_FORE_GROUND_COLOR = APPEARANCE_PREFIX+"CondDataForeGroundColor";
	public static final String ACTION_DATA_BACK_GROUND_COLOR = APPEARANCE_PREFIX+"actionDataBackGroundColor";	
	public static final String ACTION_DATA_FORE_GROUND_COLOR = APPEARANCE_PREFIX+"actionDataForeGroundColor";
	public static final String DISABLED_DATA_BACK_GROUND_COLOR = APPEARANCE_PREFIX+"DisabledDataBackGroundColor";
	public static final String DISABLED_DATA_FORE_GROUND_COLOR = APPEARANCE_PREFIX+"DisabledDataForeGroundColor";
	public static final String ALTERNATE_ROW_COLORS = APPEARANCE_PREFIX+"AlternateRowColors";
	public static final String USE_GRADIENTS = APPEARANCE_PREFIX+"UseGradients";
	public static final String SELECTED_COLOR_PALETTE = APPEARANCE_PREFIX+"SelectedColorPalette";
	public static final String COLUMN_HEADER_FONT = APPEARANCE_PREFIX+"columnHeaderFont";
	public static final String SELECTION_COLOR = APPEARANCE_PREFIX+"selectionColor";
	public static final String SELECTION_FG_COLOR = APPEARANCE_PREFIX+"selectionFgColor";
	public static final String COND_DATA_FONT = APPEARANCE_PREFIX+"condDataFont";
	public static final String ACTION_DATA_FONT = APPEARANCE_PREFIX+"actionDataFont";
	public static final String SHOW_ALIAS = APPEARANCE_PREFIX+"showAlias";
	public static final String SHOW_COLUMN_ALIAS = APPEARANCE_PREFIX+"showColumnAlias";
	public static final String REUSE_DELETED_RULE_IDS = "reuseDeletedRuleIDs";
	public static final String ALWAYS_MAINTAIN_RULE_IDS = "alwaysMaintainRuleIDs";
	public static final String SHOW_DOMAIN_DESCRIPTION = "showDomainDescription";
	public static final String AUTO_RESIZE_COLUMN = "autoResizeColumn";
	public static final String AUTO_RESIZE_ROWS = "autoResizeRows";
	public static final String SHOW_COLUMN_GROUPS = APPEARANCE_PREFIX+"showColumnGroups";
	public static final String AUTO_MERGE_VIEW = "autoMergeView";
	public static final String SHOW_ACTION_AREA_STRING = "showActionAreaString";
	public static final String SHOW_HORIZONTAL_COND_AREA_STRING = "showHorCondAreaString";
	public static final String SHOW_VER_COND_AREA_STRING = "showVerCondAreaString";
	public static final String MENU_OPTION = "menuOption";
	public static final String SHOW_ROLL_OVER_TOOL_BAR = "showRollOverToolBar";
	public static final String SHOW_CONTEXT_MENU = "showContextMenu";
	public static final String RUN_ANALYZER ="analyzer";
	public static final String CONDITION_FIELD_FILTER = "conditionFieldFilter";
	public static final String ACTION_FIELD_FILTER = "actionFieldFilter";
	public static final String FILTER_BUTTON_ON = "on";
	public static final String FILTER_BUTTON_OFF = "off";
	public static final String SHOW_COLUMN_FILTER = "showColumnFilterButton";

	public static final String TEST_RESULT_CHANGED_VALUE_BACK_GROUND_COLOR = "testResultChangedValueBackGroundColor";
	public static final String TEST_RESULT_NEW_INSTANCE_BACK_GROUND_COLOR = "testResultNewIndtanceBackgroundColor";
	public static final String TEST_RESULT_CHANGED_VALUE_FORE_GROUND_COLOR = "testResultChangedValueForeGroundColor";
	public static final String TEST_RESULT_NEW_INSTANCE_FORE_GROUND_COLOR = "testResultNewIndtanceForegroundColor";
	
	public static final String EDITOR_SECTIONS = "UseNonResizableEditorSections";
	public static final String HORIZONTAL_VERTICAL_DECISION_VIEW = "HorizontalVerticalDecisionView";
	public static final String EXISTING_ID_IMPORT = "UseExistingIDswhenimporting";
	public static final String USE_ALIAS_IN_EXPORT = "UseAliasInExport";
	public static final String VALIDATE_DURING_COMMIT = "validateDuringCommit";
	public static final String AUTO_DEPLOY_BEFORE_TEST = "AutoDeployTablesBeforeTesting";
	public static final String SHOW_RULES = "ShowRules";
	public static final String SHOW_COLUMN_ID = "ShowColumnID";
	public static final String AUTO_UPDATE_DT_VRF_ARGUMENT_CHANGE = "AutoUpdateDTOnVRFArgumentChange";
	
	public static final String CONSOLE_COLOR_DEBUG 		= "consoleColorDebug";
	public static final String CONSOLE_COLOR_INFO 		= "consoleColorInfo";
	public static final String CONSOLE_COLOR_WARN 		= "consoleColorWarn";
	public static final String CONSOLE_COLOR_ERROR 		= "consoleColorError";
	public static final String CONSOLE_COLOR_FATAL 		= "consoleColorFatal";
	public static final String CONSOLE_COLOR_DEFAULT	= "consoleColorDefault";
	public static final String CONSOLE_COLOR_STDOUT 	= "consoleColorStdout";
	public static final String CONSOLE_COLOR_STDERR		= "consoleColorStderr";

	public static final String CONSOLE_STYLE_DEBUG 		= "consoleStyleDebug";
	public static final String CONSOLE_STYLE_INFO 		= "consoleStyleInfo";
	public static final String CONSOLE_STYLE_WARN 		= "consoleStyleWarn";
	public static final String CONSOLE_STYLE_ERROR 		= "consoleStyleError";
	public static final String CONSOLE_STYLE_FATAL 		= "consoleStyleFatal";
	public static final String CONSOLE_STYLE_DEFAULT	= "consoleStyleDefault";
	public static final String CONSOLE_STYLE_STDOUT 	= "consoleStyleStdout";
	public static final String CONSOLE_STYLE_STDERR		= "consoleStyleStderr";

	public static final String PROBLEMS_MAX_ITEMS 		= "problemsMaxItems";
	public static final String PROBLEMS_SHOW_RESOURCE	= "problemsShowResource";
	public static final String PROBLEMS_SORT_ORDER		= "problemsSortOrder";
	public static final String PROBLEMS_ACTIVATE_ON_PROB= "problemsActivateOnProblem";
	
	public static final String DECISION_MANAGER_KEEP_LAST_ACTIVE_EDITORS = "decisionmanagerkeepactiveeditors";
	public static final String DECISION_MANAGER_OPENING_LAST_ACTIVE_PROJECT= "openinglastactiveproject";
	
	public static final String LINK_WITH_EDITOR = "linkWithEditor";
	
	public static final String ANALYZER_SHOW_TABLE_ANALYZER_VIEW = "analyzerIntiallyShowView";
	public static final String ANALYZER_USE_DOMAINMODEL          = "analyzerUseDomainModel";
	public static final String ANALYZER_HIGHLIGHT_PARTIAL_RANGES = "analyzerHighlightPartialRanges";
	public static final String ANALYZER_INTELLIGENT_TEST_GEN 	 = "analyzerIntelligentTestGen";
	
	public static final String DT_DOMAIN_IS_CELL_EDITABLE = "dtDomainisCellEditable";
	
//	public static final String INTEGER_RANGE_VALUE 				 = "analyzerIntegerRabfeValue";
	
	public static RGB convertToRGB(String color) {
		if (color.length() == 0) {
			color = "0,0,0";
		}
		String[] colorArray = new String[3];
		colorArray = color.split(",");
		int[] rgb = new int[3];
		for (int i = 0; i < colorArray.length; i++) {
			rgb[i] = Integer.parseInt(colorArray[i].trim());
		}
		RGB rgbColor = new RGB(rgb[0], rgb[1], rgb[2]);
		return rgbColor;
	}

}
