package com.tibco.cep.diagramming.utils;

import com.tomsawyer.graphicaldrawing.awt.TSEColor;


/**
 * @author hitesh
 *
 */

public class DiagramConstants {
	
	public static int INTERACTIVE_PREFERENCES = 0x1;
    public static int LAYOUT_PREFERENCES = 0x2;
    public static int ALL_PREFERENCES = INTERACTIVE_PREFERENCES | LAYOUT_PREFERENCES;

    public final static boolean DEBUG = false;

    public final static int UNDEFINED = -1;
    public final static int EVENT = 0;
    public final static int TIME_EVENT = 1;
    public final static int CONCEPT = 2;
    public final static int SCORECARD = 3;
    public final static int EXCEPTION = 4;

    public final static int ACTION_ENTITY_EDGE = 5;
    public final static int ENTITY_CONDITION_EDGE = 6;
    public final static int REFERENCE_EDGE = 7;
    public final static int CONTAINMENT_EDGE = 8;
    public final static int INHERITANCE_EDGE = 9;

    public final static int ACTION = 10;
    public final static int CONDITION = 11;
    public final static int RULE = 12;

    public final static int CURVED_EDGES = 13;
    public final static int POLYLINLE_EDGES = 14;

    public final static double REGULAR_EDGE_ARROWHEAD_WIDTH = 6.0;
    // public final static TSEColor TO_FROM_RULE_EDGE_COLOR = new TSEColor(255, 25, 56);
    // public final static TSEColor TO_FROM_RULE_EDGE_COLOR = RuleNodeData.END_PRESENT_COLOR;
    public final static TSEColor TO_FROM_RULE_EDGE_COLOR = new TSEColor(255, 79, 15);

    public final static TSEColor CLASSDIAGRAM_LINK_COLOR = TSEColor.gray;
    public final static TSEColor REGULAR_LINK_COLOR = TSEColor.black;
    
	//used for project diagram
	public static String PALETTE_SHOW_CONCEPTS_TITLE = Messages.getString("PALETTE_SHOW_CONCEPTS_TITLE").intern();
	public static String PALETTE_SHOW_METRICS_TITLE = Messages.getString("PALETTE_SHOW_METRICS_TITLE").intern();
	public static String PALETTE_SHOW_EVENTS_TITLE = Messages.getString("PALETTE_SHOW_EVENTS_TITLE").intern();
	public static String PALETTE_SHOW_DECISIONTABLES_TITLE = Messages.getString("PALETTE_SHOW_DECISIONTABLES_TITLE").intern();
	public static String PALETTE_SHOW_DOMAIN_MODEL_TITLE = Messages.getString("PALETTE_SHOW_DOMAIN_MODEL_TITLE").intern();
	public static String PALETTE_SHOW_STATEMACHIES_TITLE = Messages.getString("PALETTE_SHOW_STATEMACHIES_TITLE").intern();
	public static String PALETTE_SHOW_ARCHIVES_TITLE = Messages.getString("PALETTE_SHOW_ARCHIVES_TITLE").intern();
	public static String PALETTE_SHOW_RULES_TITLE = Messages.getString("PALETTE_SHOW_RULES_TITLE").intern();
	public static String PALETTE_SHOW_RULES_FUNCTIONS_TITLE = Messages.getString("PALETTE_SHOW_RULES_FUNCTIONS_TITLE").intern();
	public static String PALETTE_SHOW_SCORECARDS_TITLE = Messages.getString("PALETTE_SHOW_SCORECARDS_TITLE").intern();
	public static String PALETTE_SHOW_CHANNELS_TITLE = Messages.getString("PALETTE_SHOW_CHANNELS_TITLE").intern();
	public static String PALETTE_SHOW_PROCESSES_TITLE = Messages.getString("PALETTE_SHOW_PROCESSES_TITLE").intern();
	public static String PALETTE_SHOW_SCOPE_LINKS_TITLE = Messages.getString("PALETTE_SHOW_SCOPE_LINKS_TITLE").intern();
	public static String PALETTE_SHOW_USAGE_LINKS_TITLE = Messages.getString("PALETTE_SHOW_USAGE_LINKS_TITLE").intern();
	public static String PALETTE_SHOW_PROCESS_LINKS_TITLE = Messages.getString("PALETTE_SHOW_PROCESS_LINKS_TITLE").intern();
	public static String PALETTE_SHOW_PROCESS_EXPANDED_TITLE = Messages.getString("PALETTE_SHOW_PROCESS_EXPANDED_TITLE").intern();
	public static String PALETTE_SHOW_ARCHIVE_DEST_LINKS_TITLE = Messages.getString("PALETTE_SHOW_ARCHIVE_DEST_LINKS_TITLE").intern();
	public static String PALETTE_SHOW_ARCHIVE_RULES_LINKS_TITLE = Messages.getString("PALETTE_SHOW_ARCHIVE_RULES_LINKS_TITLE").intern();
	public static String PALETTE_SHOW_ARCHIVE_ALLRULES_LINKS_TITLE = Messages.getString("PALETTE_SHOW_ARCHIVE_ALLRULES_LINKS_TITLE").intern();
	public static String PALETTE_SHOW_RULES_FOLDERS_TITLE = Messages.getString("PALETTE_SHOW_RULES_FOLDERS_TITLE").intern();
	public static String PALETTE_SHOW_TOOLTIPS_TITLE = Messages.getString("PALETTE_SHOW_TOOLTIPS_TITLE").intern();
	public static String PALETTE_GROUP_CONCEPTS_TITLE = Messages.getString("PALETTE_GROUP_CONCEPTS_TITLE").intern();
	public static String PALETTE_GROUP_EVENTS_TITLE = Messages.getString("PALETTE_GROUP_EVENTS_TITLE").intern();
	public static String PALETTE_GROUP_RULES_TITLE = Messages.getString("PALETTE_GROUP_RULES_TITLE").intern();
	public static String PALETTE_GROUP_RULE_FUNCTIONS_TITLE = Messages.getString("PALETTE_GROUP_RULE_FUNCTIONS_TITLE").intern();
	
	//used for project diagram
	public static String PALETTE_SHOW_CONCEPTS_TOOLTIP = Messages.getString("PALETTE_SHOW_CONCEPTS_TOOLTIP");
	public static String PALETTE_SHOW_METRICS_TOOLTIP = Messages.getString("PALETTE_SHOW_METRICS_TOOLTIP");
	public static String PALETTE_SHOW_EVENTS_TOOLTIP = Messages.getString("PALETTE_SHOW_EVENTS_TOOLTIP");
	public static String PALETTE_SHOW_DECISIONTABLES_TOOLTIP = Messages.getString("PALETTE_SHOW_DECISIONTABLES_TOOLTIP");
	public static String PALETTE_SHOW_DOMAIN_MODEL_TOOLTIP = Messages.getString("PALETTE_SHOW_DOMAIN_MODEL_TOOLTIP");
	public static String PALETTE_SHOW_STATEMACHIES_TOOLTIP = Messages.getString("PALETTE_SHOW_STATEMACHIES_TOOLTIP");
	public static String PALETTE_SHOW_ARCHIVES_TOOLTIP = Messages.getString("PALETTE_SHOW_ARCHIVES_TOOLTIP");
	public static String PALETTE_SHOW_RULES_TOOLTIP = Messages.getString("PALETTE_SHOW_RULES_TOOLTIP");
	public static String PALETTE_SHOW_RULES_FUNCTIONS_TOOLTIP = Messages.getString("PALETTE_SHOW_RULES_FUNCTIONS_TOOLTIP");
	public static String PALETTE_SHOW_SCORECARDS_TOOLTIP = Messages.getString("PALETTE_SHOW_SCORECARDS_TOOLTIP");
	public static String PALETTE_SHOW_CHANNELS_TOOLTIP = Messages.getString("PALETTE_SHOW_CHANNELS_TOOLTIP");
	public static String PALETTE_SHOW_PROCESSES_TOOLTIP = Messages.getString("PALETTE_SHOW_PROCESSES_TOOLTIP").intern();
	public static String PALETTE_SHOW_SCOPE_LINKS_TOOLTIP = Messages.getString("PALETTE_SHOW_SCOPE_LINKS_TOOLTIP");
	public static String PALETTE_SHOW_USAGE_LINKS_TOOLTIP = Messages.getString("PALETTE_SHOW_USAGE_LINKS_TOOLTIP");
	public static String PALETTE_SHOW_PROCESS_LINKS_TOOLTIP = Messages.getString("PALETTE_SHOW_PROCESS_LINKS_TOOLTIP").intern();
	public static String PALETTE_SHOW_PROCESS_EXPANDED_TOOLTIP = Messages.getString("PALETTE_SHOW_PROCESS_EXPANDED_TOOLTIP").intern();
	public static String PALETTE_SHOW_ARCHIVE_DEST_LINKS_TOOLTIP = Messages.getString("PALETTE_SHOW_ARCHIVE_DEST_LINKS_TOOLTIP");
	public static String PALETTE_SHOW_ARCHIVE_RULES_LINKS_TOOLTIP = Messages.getString("PALETTE_SHOW_ARCHIVE_RULES_LINKS_TOOLTIP");
	public static String PALETTE_SHOW_ARCHIVE_ALLRULES_LINKS_TOOLTIP = Messages.getString("PALETTE_SHOW_ARCHIVE_ALLRULES_LINKS_TOOLTIP");
	public static String PALETTE_SHOW_RULES_FOLDERS_TOOLTIP = Messages.getString("PALETTE_SHOW_RULES_FOLDERS_TOOLTIP");
	public static String PALETTE_SHOW_TOOLTIPS_TOOLTIP = Messages.getString("PALETTE_SHOW_TOOLTIPS_TOOLTIP");
	public static String PALETTE_GROUP_CONCEPTS_TOOLTIP = Messages.getString("PALETTE_GROUP_CONCEPTS_TOOLTIP");
	public static String PALETTE_GROUP_EVENTS_TOOLTIP = Messages.getString("PALETTE_GROUP_EVENTS_TOOLTIP");
	public static String PALETTE_GROUP_RULES_TOOLTIP = Messages.getString("PALETTE_GROUP_RULES_TOOLTIP");
	public static String PALETTE_GROUP_RULE_FUNCTIONS_TOOLTIP = Messages.getString("PALETTE_GROUP_RULE_FUNCTIONS_TOOLTIP");
	public static String PALETTE_COLLAPSE_PROCESSES_TOOLTIP = Messages.getString("PALETTE_COLLAPSE_PROCESSES_TOOLTIP").intern();
	
	//used for dependency diagram
	public static String PALETTE_DEPENDENCY_LEVEL_ONE_TITLE = Messages.getString("PALETTE_DEPENDENCY_LEVEL_ONE").intern();
	public static String PALETTE_DEPENDENCY_LEVEL_TWO_TITLE = Messages.getString("PALETTE_DEPENDENCY_LEVEL_TWO").intern();
	public static String PALETTE_DEPENDENCY_LEVEL_ALL_TITLE = Messages.getString("PALETTE_DEPENDENCY_LEVEL_ALL").intern();
	
	//used for dependency diagram
	public static String PALETTE_DEPENDENCY_LEVEL_ONE_TOOLTIP = Messages.getString("PALETTE_DEPENDENCY_LEVEL_ONE_TOOLTIP");
	public static String PALETTE_DEPENDENCY_LEVEL_TWO_TOOLTIP = Messages.getString("PALETTE_DEPENDENCY_LEVEL_TWO_TOOLTIP");
	public static String PALETTE_DEPENDENCY_LEVEL_ALL_TOOLTIP = Messages.getString("PALETTE_DEPENDENCY_LEVEL_ALL_TOOLTIP");

}
