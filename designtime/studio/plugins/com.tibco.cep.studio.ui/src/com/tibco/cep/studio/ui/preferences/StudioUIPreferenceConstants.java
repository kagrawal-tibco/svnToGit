package com.tibco.cep.studio.ui.preferences;

/**
 * 
 * @author sasahoo
 *
 */
public class StudioUIPreferenceConstants {

	public static final String AUTO_DEPLOY_BEFORE_TEST = "AutoDeployTablesBeforeTesting";
	public static final String TEST_DATA_INPUT_PATH = "TestDataInputPath";
	public static final String TEST_DATA_OUTPUT_PATH = "TestDataOutputPath";
	public static final String WM_OBJCECTS_SHOW_MAX_NO = "WMObjectsMax";
	public static final String AUTO_SCROLL_RESULT_TABLE = "AutoScrollResultTable";
	
	// General
	public static final String CATALOG_FUNCTION_SHOW_TOOLTIPS="catalog.function.tooltip.show";
	public static final String OPEN_CONFIRM_PERSPECTIVE_CHANGE="open.show.perspective.show";
	public static final String MIGRATE_OLD_PROJECTS_HIDE="migrate.old.projects.hide";
	public static final String SWITCH_PERSPECTIVE_ON_EDITOR_ACTIVATION="switch.perspective.on.editor.activation";
	public static final String STUDIO_PERSIST_ENTITIES_AS_SOURCE= "studio.persist.entities.as.source";

	// 'Do not show again' settings
//	public static final String SWITCH_PERSPECTIVE_ON_EDITOR_ACTIVATION_HIDE="switch.perspective.on.editor.activation.hide";
	public static final String[] DO_NOT_SHOW_PREFS = new String[] { OPEN_CONFIRM_PERSPECTIVE_CHANGE, MIGRATE_OLD_PROJECTS_HIDE };
	
	//Show warnings
	public static final String STUDIO_SHOW_WARNINGS = "studio.show.warning";
	
	//List
	public static final String STUDIO_SHOW_WARNINGS_IGNORE_PATTERNS = "studio.show.warning.pattern";
	
	public static final String STUDIO_OVERWRITE_EAR = "studio.overwrite.ear";
	public static final String STUDIO_OVERWRITE_POM = "studio.overwrite.pom";
	
}