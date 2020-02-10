//
//	SelectToolConstants.java
//
//	Copyright:
//		Tom Sawyer Software, 1992 - 2005
//		1625 Clay Street
//		Sixth Floor
//		Oakland, CA 94612
//		U.S.A.
//
//		Web: www.tomsawyer.com
//
//		All Rights Reserved.
//

package com.tibco.cep.diagramming.tool.popup;


/**
 * This interface defines all constants used by Tomahawk.
 */
public interface EntityResourceConstants
{

// --- LOOK AND FEEL --------------------------------------------------

	/**
	 * This constant instructs the application to choose the Metal Look
	 * and Feel (also known as the Java Look and Feel)
	 */
	public static final String METAL = "metal";

	/**
	 * This constant instructs the application to choose the Windows
	 * Look and Feel.
	 */
	public static final String WINDOWS = "windows";

	/**
	 * This constant instructs the application to choose the Motif
	 * Look and Feel.
	 */
	public static final String MOTIF = "motif";

	/**
	 * This constant instructs the application to choose the Mac Look
	 * and Feel.
	 */
	public static final String MAC = "mac";

	/**
	 * This constant instructs the application to choose the platform
	 * specific Look and Feel.
	 */
	public static final String SYSTEM = "system";


// --- ACTIONS ---------------------------------------------------------

	public static final String ELEMENTDIAGRAM = "ELEMENT_DIAGRAM";
	/**
	 * This command string instructs the application to abort any
	 * action that it is currently carrying out.
	 */
	public static final String ACTION_ABORT = "ACTION_ABORT";

	/**
	 * This command string instructs the application to add a label to
	 * the currently selected edge.
	 */
	public static final String ADD_EDGE_LABEL = "ADD_EDGE_LABEL";

	/**
	 * This command string instructs the application to add a label to
	 * the currently selected node.
	 */
	public static final String ADD_NODE_LABEL = "ADD_NODE_LABEL";

	/**
	 * This command string instructs the application to add a label to
	 * the currently selected connector.
	 */
	public static final String ADD_CONNECTOR_LABEL = "ADD_CONNECTOR_LABEL";

	/**
	 * This command string instructs the application to add a connector
	 * to the currently selected node.
	 */
	public static final String ADD_NODE_CONNECTOR = "ADD_NODE_CONNECTOR";

	/**
	 * This command string instructs the application to open a URL
	 * in the default browser of the client.
	 */
	public static final String OPEN_URL = "OPEN_URL";

	
	/**
	 * This command string instructs the application to display the
	 * about message box.
	 */
	public static final String APP_ABOUT = "APP_ABOUT";

	/**
	 * This command string instructs the application to open Tom Sawyer
	 * support page.
	 */
	public static final String APP_SUPPORT = "APP_SUPPORT";

	/**
	 * This command string instructs the application to terminate.
	 */
	public static final String APP_EXIT = "APP_EXIT";

	/**
	 * This command string instructs the application to perform an operation
	 * on the current graph.
	 */
	public static final String APPLY_OPERATION = "APPLY_OPERATION";

	/**
	 * This command string instructs the application to apply the operation on
	 * all edges.
	 */
	public static final String ALL_EDGES = "ALL_EDGES";
	 
	/**
	 * This command string instructs the application to apply the operation on
	 * selected edges only.
	 */
	public static final String SELECTED_EDGES = "SELECTED_EDGES";

	/**
	 * This command string instructs the application to clear the
	 * current graph.
	 */
	public static final String CLEAR_ALL = "CLEAR_ALL";

	/**
	 * This command string instructs the application to clear the
	 * undo/redo history.
	 */
	public static final String CLEAR_HISTORY = "CLEAR_HISTORY";
	
	/**
	 * This command string instructs the application to collapse
	 * the child graph associated with a node.
	 */
	public static final String COLLAPSE = "COLLAPSE";

	/**
	 * This command string instructs the application to collapse all
	 * nested child graphs.
	 */
	public static final String COLLAPSE_ALL = "COLLAPSE_ALL";

	/**
	 * This command string instructs the application to collapse
	 * the child graphs associated with the selected nodes.
	 */
	public static final String COLLAPSE_SELECTED = "COLLAPSE_SELECTED";

	/**
	 * This command string instructs the application to copy the
	 * selected objects to the clipboard.
	 */
	public static final String COPY_GRAPH = "COPY_GRAPH";

	/**
	 * This command string instructs the application to edit the
	 * selected node. (opens the applicable form editor)
	 */
	public static final String EDIT_NODE = "EDIT_NODE";
	/**
	 * This command string instructs the application to create a child
	 * graph for a node or edge.
	 */
	public static final String CREATE_CHILD_GRAPH =
		"CREATE_CHILD_GRAPH";

	/**
	 * This command string instructs the application to cut the
	 * selected objects from the graph manager and place them 
	 * in the clipboard.
	 */
	public static final String CUT_GRAPH = "CUT_GRAPH";

	/**
	 * This command string instructs the application to show the dependency diagram
	 * the selected objects
	 */
	public static final String SHOW_DEPENDENCY_GRAPH = "SHOW_DEPENDENCY_GRAPH";
	
	/**
	 * This command string instructs the application to delete the
	 * child graph of the currently selected node or edge.
	 */
	public static final String DELETE_CHILD_GRAPH =
		"DELETE_CHILD_GRAPH";

	/**
	 * This command string instructs the application to delete the
	 * selected objects from the graph manager.
	 */
	public static final String DELETE_SELECTED = "DELETE_SELECTED";

	/**
	 * This command string instructs the application to delete all
	 * connectors from the currently selected node.
	 */
	public static final String DELETE_NODE_CONNECTORS =
		"DELETE_NODE_CONNECTORS";
	
	/**
	 * This command string instructs the application to show the
	 * Drawing Preferences dialog
	 */
	public static final String DRAWING_PREFERENCES = "DRAWING_PREFERENCES";
	
	/**
	 * This command string instructs the application to hide the
	 * slected graph.
	 */
	public static final String HIDE_SELECTED = "HIDE_SELECTED";

	/**
	 * This command string instructs the application to hide one level
	 * childrens of the selected node.
	 */
	public static final String HIDE_CHILDREN_ONE_LEVEL =
		"HIDE_CHILDREN_ONE_LEVEL";
	
	/**
	 * This command string instructs the application to hide `n` level
	 * childrens of the selected node.
	 */
	public static final String HIDE_CHILDREN_N_LEVEL =
		"HIDE_CHILDREN_N_LEVEL";
	
	/**
	 * This command string instructs the application to hide all level
	 * childrens of the selected node.
	 */
	public static final String HIDE_CHILDREN_ALL_LEVEL =
		"HIDE_CHILDREN_ALL_LEVEL";
	
	/**
	 * This command string instructs the application to hide one level
	 * parents of the selected node.
	 */
	public static final String HIDE_PARENTS_ONE_LEVEL =
		"HIDE_PARENTS_ONE_LEVEL";
	
	/**
	 * This command string instructs the application to hide `n` level
	 * parents of the selected node.
	 */
	public static final String HIDE_PARENTS_N_LEVEL =
		"HIDE_PARENTS_N_LEVEL";
	
	/**
	 * This command string instructs the application to hide all level
	 * parents of the selected node.
	 */
	public static final String HIDE_PARENTS_ALL_LEVEL =
		"HIDE_PARENTS_ALL_LEVEL";
	
	/**
	 * This command string instructs the application to hide one level
	 * neighbors of the selected node.
	 */
	public static final String HIDE_NEIGHBORS_ONE_LEVEL =
		"HIDE_NEIGHBORS_ONE_LEVEL";
	
	/**
	 * This command string instructs the application to hide n level
	 * neighbors of the selected node.
	 */
	public static final String HIDE_NEIGHBORS_N_LEVEL =
		"HIDE_NEIGHBORS_N_LEVEL";
	
	/**
	 * This command string instructs the application to hide all 
	 * neighbors of the selected node.
	 */
	public static final String HIDE_NEIGHBORS_ALL_LEVEL =
		"HIDE_NEIGHBORS_ALL_LEVEL";

	/**
	 * This command string instructs the application to hide all 
	 * incident edges of the selected node.
	 */
	public static final String HIDE_INCIDENT_EDGES =
		"HIDE_INCIDENT_EDGES";
	
	/**
	 * This command string instructs the application to unhide one level
	 * childrens of the selected node.
	 */
	public static final String UNHIDE_CHILDREN_ONE_LEVEL =
		"UNHIDE_CHILDREN_ONE_LEVEL";
	
	/**
	 * This command string instructs the application to unhide `n` level
	 * childrens of the selected node.
	 */
	public static final String UNHIDE_CHILDREN_N_LEVEL =
		"UNHIDE_CHILDREN_N_LEVEL";
	
	/**
	 * This command string instructs the application to unhide all
	 * childrens of the selected node.
	 */
	public static final String UNHIDE_CHILDREN_ALL_LEVEL =
		"UNHIDE_CHILDREN_ALL_LEVEL";
	
	/**
	 * This command string instructs the application to unhide one level
	 * parents of the selected node.
	 */
	public static final String UNHIDE_PARENTS_ONE_LEVEL =
		"UNHIDE_PARENTS_ONE_LEVEL";
	
	/**
	 * This command string instructs the application to unhide `n` level
	 * parents of the selected node.
	 */
	public static final String UNHIDE_PARENTS_N_LEVEL =
		"UNHIDE_PARENTS_N_LEVEL";
	
	/**
	 * This command string instructs the application to unhide all 
	 * parents of the selected node.
	 */
	public static final String UNHIDE_PARENTS_ALL_LEVEL =
		"UNHIDE_PARENTS_ALL_LEVEL";
	
	/**
	 * This command string instructs the application to unhide one level
	 * neighbors of the selected node.
	 */
	public static final String UNHIDE_NEIGHBORS_ONE_LEVEL =
		"UNHIDE_NEIGHBORS_ONE_LEVEL";
	
	/**
	 * This command string instructs the application to unhide `n` level
	 * neighbors of the selected node.
	 */
	public static final String UNHIDE_NEIGHBORS_N_LEVEL =
		"UNHIDE_NEIGHBORS_N_LEVEL";
	
	/**
	 * This command string instructs the application to unhide all 
	 * neighbors of the selected node.
	 */
	public static final String UNHIDE_NEIGHBORS_ALL_LEVEL =
		"UNHIDE_NEIGHBORS_ALL_LEVEL";

	/**
	 * This command string instructs the application to unhide all 
	 * incident edges of the selected node.
	 */
	public static final String UNHIDE_INCIDENT_EDGES =
		"UNHIDE_INCIDENT_EDGES";

	/**
	 * This command string instructs the application to unhide the
	 * hidden graph.
	 */
	public static final String UNHIDE_ALL = "UNHIDE_ALL";
	
	/**
	 * This command string instructs the application to fold the
	 * selected graph.
	 */
	public static final String FOLD_SELECTED = "FOLD_SELECTED";

	/**
	 * This command string instructs the application to fold one level
	 * childrens of the selected node.
	 */
	public static final String FOLD_CHILDREN_ONE_LEVEL =
		"FOLD_CHILDREN_ONE_LEVEL";
	
	/**
	 * This command string instructs the application to fold `n` level
	 * childrens of the selected node.
	 */
	public static final String FOLD_CHILDREN_N_LEVEL =
		"FOLD_CHILDREN_N_LEVEL";
	
	/**
	 * This command string instructs the application to fold all level
	 * childrens of the selected node.
	 */
	public static final String FOLD_CHILDREN_ALL_LEVEL =
		"FOLD_CHILDREN_ALL_LEVEL";
	
	/**
	 * This command string instructs the application to fold one level
	 * parents of the selected node.
	 */
	public static final String FOLD_PARENTS_ONE_LEVEL =
		"FOLD_PARENTS_ONE_LEVEL";
	
	/**
	 * This command string instructs the application to fold `n` level
	 * parents of the selected node.
	 */
	public static final String FOLD_PARENTS_N_LEVEL =
		"FOLD_PARENTS_N_LEVEL";
	
	/**
	 * This command string instructs the application to fold all level
	 * parents of the selected node.
	 */
	public static final String FOLD_PARENTS_ALL_LEVEL =
		"FOLD_PARENTS_ALL_LEVEL";
	
	/**
	 * This command string instructs the application to fold one level
	 * neigbors of the selected node.
	 */
	public static final String FOLD_NEIGHBORS_ONE_LEVEL =
		"FOLD_NEIGHBORS_ONE_LEVEL";
	
	/**
	 * This command string instructs the application to fold `n` level
	 * neighbors of the selected node
	 */
	public static final String FOLD_NEIGHBORS_N_LEVEL =
		"FOLD_NEIGHBORS_N_LEVEL";
	
	/**
	 * This command string instructs the application to fold all 
	 * neighbors of the selected node.
	 */
	public static final String FOLD_NEIGHBORS_ALL_LEVEL =
		"FOLD_NEIGHBORS_ALL_LEVEL";
	
	/**
	 * This command string instructs the application to unfold the
	 * all folded nodes.
	 */
	public static final String UNFOLD_ALL = "UNFOLD_ALL";

	/**
	 * This command string instructs the application to unfold the
	 * selected graph.
	 */
	public static final String UNFOLD_SELECTED = "UNFOLD_SELECTED";

	/**
	 * This command string instructs the application to unfold the
	 * selected folder.
	 */
	public static final String UNFOLD = "UNFOLD";
	public static final String UNFOLDALL = "UNFOLDALL";		
	/**
	 * This command string instructs the application to enable docking
	 * of secondary windows.
	 */
	public static final String DOCKABLE_WINDOWS = "DOCKABLE_WINDOWS";

	/**
	 * This command string instructs the application to change the
	 * the view of the Tomahawk client area to use tabbed windows.
	 */
	public static final String TABBED_WINDOWS = "TABBED_WINDOWS";
	
	/**
	 * This command string instructs the application to select the next
	 * swing canvas and move it to the front.
	 */
	public static final String NEXT_CANVAS = "NEXT_CANVAS";

	/**
	 * This command string instructs the application to duplicate all
	 * selected objects in the graph manager.
	 */
	public static final String DUPLICATE_GRAPH = "DUPLICATE_GRAPH";

	/**
	 * This command string instructs the application to edit the text 
	 * (tag) of the selected objects.
	 */
	public static final String EDIT_TEXT = "EDIT_TEXT";

	/**
	 * This command string instructs the application to expand the
	 * child graph associated with a node.
	 */
	public static final String EXPAND = "EXPAND";

	/**
	 * This command string instructs the application to expand all
	 * nested child graphs.
	 */
	public static final String EXPAND_ALL = "EXPAND_ALL";

	/**
	 * This command string instructs the application to expand
	 * the child graphs associated with the selected nodes.
	 */
	public static final String EXPAND_SELECTED = "EXPAND_SELECTED";

	/**
	 * This command string instructs the application to switch to the
	 * child graph of the selected node or edge.
	 */
	public static final String GOTO_CHILD = "GOTO_CHILD";

	/**
	 * This command string instructs the application to switch to the
	 * parent graph of the current graph.
	 */
	public static final String GOTO_PARENT = "GOTO_PARENT";

	/**
	 * This command string instructs the application to display the
	 * root (top most) graph of the graph hierarchy.
	 */
	public static final String GOTO_ROOT = "GOTO_ROOT";

	/**
	 * This command string instructs the application to change the
	 * size of the grid of the swing canvas.
	 */
	public static final String GRID_SIZE = "GRID_SIZE";

	/**
	 * This command string instructs the application to set the user
	 * specified size of the grid of the swing canvas.
	 */
	public static final String GRID_SIZE_CUSTOM =
		"GRID_SIZE_CUSTOM";

	/**
	 * This command string instructs the application to change the
	 * visibility or type of the grid of the swing canvas.
	 */
	public static final String GRID_TYPE = "GRID_TYPE";

	/**
	 * This command string instructs the application to apply an
	 * incremental layout to the graph.
	 */
	public static final String INCREMENTAL_LAYOUT =
		"INCREMENTAL_LAYOUT";


	/**
	 * This command string instructs the application to apply a
	 * random layout to the graph.
	 */
	public static final String RANDOM_LAYOUT =
		"RANDOM_LAYOUT";

	/**
	 * This command string instructs the application to open an
	 * inspector window.
	 */
	public static final String INSPECTOR_WINDOW =
		"INSPECTOR_WINDOW";

    /**
	 * This command string instructs the application to open an
	 * constraint window.
	 */
	public static final String CONSTRAINT_WINDOW =
		"CONSTRAINT_WINDOW";


    /**
	 * This command string instructs the application to load a graph
	 * from a file.
	 */
	public static final String LOAD_GRAPH = "LOAD_GRAPH";


	/**
	 * This command string instructs the application to import a graph
	 * from a file.
	 */
	public static final String IMPORT_GRAPH = "IMPORT_GRAPH";


	/**
	 * This command string instructs the application to export a graph
	 * to a file.
	 */
	public static final String EXPORT_GRAPH = "EXPORT_GRAPH";


    /**
	 * This command string instructs the application to close a graph.
	 */
	public static final String CLOSE_GRAPH = "CLOSE_GRAPH";

	 /**
	 * This command string instructs the application to close a graph.
	 */
	public static final String CLOSE_ALL_GRAPHS = "CLOSE_ALL_GRAPHS";

	 /**
	 * This command string instructs the application to close a graph.
	 */
	public static final String CLOSE_ALL_GRAPHS_BUT_THIS = "CLOSE_ALL_GRAPHS_BUT_THIS";

	/**
	 * This command string instructs the application to move the 
	 * selection to the left.
	 */
	public static final String MOVE_LEFT = "MOVE_LEFT";

	/**
	 * This command string instructs the application to move the 
	 * selection to the right.
	 */
	public static final String MOVE_RIGHT = "MOVE_RIGHT";

	/**
	 * This command string instructs the application to move the 
	 * selection up.
	 */
	public static final String MOVE_UP = "MOVE_UP";

	/**
	 * This command string instructs the application to move the 
	 * selection down.
	 */
	public static final String MOVE_DOWN = "MOVE_DOWN";

	/**
	 * This command string instructs the application that the 
	 * selection move is done.
	 */
	public static final String MOVE_DONE = "MOVE_DONE";

	/**
	 * This command string instructs the application to create a new
	 * graph.
	 */
	public static final String NEW_GRAPH = "NEW_GRAPH";

	/**
	 * This command string instructs the application to open an
	 * overview window.
	 */
	public static final String OVERVIEW_WINDOW = "OVERVIEW_WINDOW";

	/**
	 * This command string instructs the application to open a
	 * node palette window.
	 */
	public static final String PALETTE_WINDOW = "PALETTE_WINDOW";

	/**
	 * This command string instructs the application to paste the
	 * contents of the clipboard into the graph manager.
	 */
	public static final String PASTE_GRAPH = "PASTE_GRAPH";

	/**
	 * This command string instructs the application to print the
	 * graph.
	 */
	public static final String PRINT_GRAPH = "PRINT_GRAPH";

	/**
	 * This command string instructs the application to show the print
	 * preview window.
	 */
	public static final String PRINT_PREVIEW = "PRINT_PREVIEW";
	
	/**
	 * This command string instructs the application to show the print
	 * setup dialog.
	 */
	public static final String PRINT_SETUP = "PRINT_SETUP";
	public static final String PRINT = "PRINT";	
	/**
	 * This command string instructs the application to redo the last
	 * undone action.
	 */
	public static final String REDO = "REDO";

	/**
	 * This command string instructs the application to refresh the
	 * swing canvas.
	 */
	public static final String REFRESH_GRAPH = "REFRESH_GRAPH";

	/**
	 * This command string instructs the application to revert all the
	 * changes made to the current graph after it has been loaded.
	 */
	public static final String REVERT_GRAPH = "REVERT_GRAPH";

	/**
	 * This command string instructs the application to toggle run mode.
	 */
	public static final String RUN_MODE = "RUN_MODE";

	/**
	 * This command string instructs the application to save the graph
	 * to a file.
	 */
	public static final String SAVE_GRAPH = "SAVE_GRAPH";

	/**
	 * This command string instructs the application to save the graph
	 * to a file with a given name and in a given format.
	 */
	public static final String SAVE_GRAPH_AS = "SAVE_GRAPH_AS";
	
	/**
	 * This command string instructs the application to save the graph
	 * to an image file with a given name and in a given format.
	 */
	public static final String SAVE_GRAPH_AS_IMAGE =
		"SAVE_GRAPH_AS_IMAGE";

	/**
	 * This command string instructs the application to scroll the view
	 * to the left.
	 */
	public static final String SCROLL_LEFT = "SCROLL_LEFT";

	/**
	 * This command string instructs the application to scroll the view
	 * to the right.
	 */
	public static final String SCROLL_RIGHT = "SCROLL_RIGHT";

	/**
	 * This command string instructs the application to scroll the view
	 * up.
	 */
	public static final String SCROLL_UP = "SCROLL_UP";

	/**
	 * This command string instructs the application to scroll the view
	 * down.
	 */
	public static final String SCROLL_DOWN = "SCROLL_DOWN";

	/**
	 * This command string instructs the application to select all
	 * objects in the graph.
	 */
	public static final String SELECT_ALL = "SELECT_ALL";

	/**
	 * This command string instructs the application to select all
	 * edges in the graph.
	 */
	public static final String SELECT_EDGES = "SELECT_EDGES";

	/**
	 * This command string instructs the application to select all
	 * labels in the graph manager.
	 */
	public static final String SELECT_LABELS = "SELECT_LABELS";

	/**
	 * This command string instructs the application to select all
	 * nodes in the graph manager.
	 */
	public static final String SELECT_NODES = "SELECT_NODES";

	/**
	 * This command string instructs the application to set the
	 * background image of the current graph.
	 */
	public static final String SET_BACKGROUND_IMAGE =
		"SET_BACKGROUND_IMAGE";

	/**
	 * This command string instructs the application to snap all
	 * selected nodes, bends, and labels to the grid.
	 */
	public static final String SNAP_TO_GRID = "SNAP_TO_GRID";

	/**
	 * This command string instructs the application to undo the last
	 * executed action.
	 */
	public static final String UNDO = "UNDO";

	/**
	 * This command string instructs the application to select the
	 * magnification factor specified by the command suffix. If no
	 * suffix is specified the default magnification is 100%.
	 */
	public static final String ZOOM = "ZOOM";

	/**
	 * This command string instructs the application to auto fit in 
	 * canvas. In every graph change, graph will be fit to swing canvas.
	 */
	public static final String ZOOM_AUTO_FIT = "AUTO_FIT";
	
	/** 
	 * This constant instructs the application that the user
	 * has changed the zoom level.
	 */
	public static final String ZOOM_CHANGE = "ZOOM_CHANGE";

	/**
	 * This command string instructs the application to set the user
	 * specified magnification factor.
	 */
	public static final String ZOOM_CUSTOM = "ZOOM_CUSTOM";

	/**
	 * This command string instructs the application to select the
	 * magnification factor that makes the graph fit in the current
	 * canvas.
	 */
	public static final String FIT_IN_WINDOW = "FIT_IN_WINDOW";

	/**
	 * This constant instructs the application to increase the
	 * magnification factor.
	 */
	public static final String ZOOM_IN = "ZOOM_IN";

	/**
	 * This constant instructs the application to decrease the
	 * magnification factor.
	 */
	public static final String ZOOM_OUT = "ZOOM_OUT";
	
	public static final String FOLD_CUSTOM = "FOLD_CUSTOM";	

	public static final String EXPORT_IMAGE = "EXPORT_IMAGE";	
	
// --- TOOLS ---------------------------------------------------------

	/**
	 * This command string instructs the application to switch to the
	 * node creation TOOL.
	 */
	public static final String CREATE_NODE_TOOL = "CREATE_NODE_TOOL";

	/**
	 * This command string instructs the application to switch to the
	 * edge creation TOOL.
	 */
	public static final String CREATE_EDGE_TOOL = "CREATE_EDGE_TOOL";

	/**
	 * This command string instructs the application to switch to the
	 * selection TOOL.
	 */
	public static final String SELECT_TOOL = "SELECT_TOOL";


	/**
	 * This command string instructs the application to switch to the
	 * zoom TOOL.
	 */
	public static final String ZOOM_TOOL = "ZOOM_TOOL";

	/**
	 * This command string instructs the application to switch to the
	 * interactive zoom TOOL.
	 */
	public static final String INTERACTIVE_ZOOM_TOOL =
		"INTERACTIVE_ZOOM_TOOL";

	/**
	 * This command string instructs the application to switch to the
	 * panning TOOL.
	 */
	public static final String PAN_TOOL = "PAN_TOOL";

	/**
	 * This command string instructs the application to switch to the
	 * next tool.
	 */
	public static final String NEXT_TOOL = "NEXT_TOOL";

	/**
	 * This command string instructs the application to switch to the
	 * next tool set.
	 */
	public static final String NEXT_TOOL_SET = "NEXT_TOOL_SET";

	/**
	 * This command string instructs the application to switch into
	 * link navigation TOOL.
	 */
	 public static final String EDGE_NAVIGATION_TOOL =
		"EDGE_NAVIGATION_TOOL";
    

// --- Default Node and Edge UIs --------------------------------------

	/**
	 * This constant defines the name for the default node UI type.
	 */
	public static final String DEFAULT_NODE_UI = "nodeUI.default";

	/**
	 * This constant defines the name for the default edge UI type.
	 */
	public static final String DEFAULT_EDGE_UI = "edgeUI.default";

	/**
	 * This constant defines the name for the default label UI type.
	 */
	public static final String DEFAULT_LABEL_UI = "labelUI.default";


// --- Recognized popups ----------------------------------------------

	/**
	 * This constant defines the command string associated with a node
	 * popup menu.
	 */
	public static final String NODE_POPUP = "NODE_POPUP";
	
	/**
	 * This constant defines the command string associated with a connector
	 * popup menu.
	 */
	public static final String CONNECTOR_POPUP = "CONNECTOR_POPUP";

	/**
	 * This constant defines the command string associated with an edge
	 * popup menu.
	 */
	public static final String EDGE_POPUP = "EDGE_POPUP";

	/**
	 * This constant defines the command string associated with an edge 
	 * label popup menu.
	 */
	public static final String EDGE_LABEL_POPUP = "EDGE_LABEL_POPUP";

	/**
	 * This constant defines the command string associated with a node
	 * label popup menu.
	 */
	public static final String NODE_LABEL_POPUP = "NODE_LABEL_POPUP";

	/**
	 * This constant defines the command string associated with a connector
	 * label popup menu.
	 */
	public static final String CONNECTOR_LABEL_POPUP = "CONNECTOR_LABEL_POPUP";

	/**
	 * This constant defines the command string associated with a bend
	 * popup menu.
	 */
	public static final String BEND_POPUP = "BEND_POPUP";
	
	/**
	 * This constant defines the command string associated with a graph
	 * popup menu.
	 */
	public static final String GRAPH_POPUP = "GRAPH_POPUP";

	/**
	 * This constant defines the command string associated with a tab
	 * popup menu.
	 */
	public static final String TAB_POPUP = "TAB_POPUP";
	
	
	// --- BPMN options -------------------------------------------------
	
	public static final String HIT_IN_BREAKPOINT = "HIT_IN_BREAKPOINT";
	public static final String HIT_OUT_BREAKPOINT = "HIT_OUT_BREAKPOINT";

	public static final String REMOVE_IN_BREAKPOINT = "REMOVE_IN_BREAKPOINT";
	public static final String REMOVE_OUT_BREAKPOINT = "REMOVE_OUT_BREAKPOINT";
	
	public static final String REMOVE_ALL_BREAKPOINTS = "REMOVE_ALL_BREAKPOINTS";
	
	public static final String PROCESS_BREAKPOINT_PROPERTIES = "PROCESS_BREAKPOINT_PROPERTIES";

// --- Tailor options -------------------------------------------------

	/**
	 * This constant defines the command string associated with
	 * the layout properties dialog.
	 */
	 public static final String LAYOUT_PROPERTIES = "LAYOUT_PROPERTIES";

	
// --- Placeholder strings --------------------------------------------

	/**
	 * This placeholder string will be replaced by a number.
	 */
	public static final String X_PLACEHOLDER = "%X%";

	/**
	 * This placeholder string will be replaced by a number.
	 */
	public static final String Y_PLACEHOLDER = "%Y%";

	/**
	 * This placeholder string will be replaced by a filename.
	 */
	public static final String FILENAME_PLACEHOLDER =
		"%FILENAME%";

	/**
	 * This placeholder string will be replaced by the Tom Sawyer
	 * Visualization for Java version number.
	 */
	public static final String TSVJ_VERSION_PLACEHOLDER =
		"%TSVJ_VERSION%";

	/**
	 * This placeholder string will be replaced by the Tom Sawyer
	 * Visualization for Java major version number.
	 */
	public static final String TSVJ_MAJOR_VERSION_PLACEHOLDER =
		"%TSVJ_MAJOR_VERSION%";

	/**
	 * This placeholder string will be replaced by the 
	 * layout version number.
	 */
	public static final String LAYOUT_VERSION_PLACEHOLDER =
		"%LAYOUT_VERSION%";

	/**
	 * This placeholder string will be replaced by the 
	 * available layout styles.
	 */
	public static final String LAYOUT_STYLES_PLACEHOLDER =
		"%LAYOUT_STYLES%";

	/**
	 * This placeholder string will be replaced by the layout
	 * server type.
	 */
	public static final String LAYOUT_SERVER_TYPE_PLACEHOLDER =
		"%LAYOUT_SERVER_TYPE%";

	/**
	 * This placeholder string will be replaced by the Java VM version
	 * number.
	 */
	public static final String JAVA_VERSION_PLACEHOLDER =
		"%JAVA_VERSION%";

	/**
	 * This placeholder string will be replaced by the Tom Sawyer
	 * Visualization for Java licensing track.
	 */
	public static final String TSVJ_TRACK_PLACEHOLDER =
		"%TSVJ_TRACK%";

	/**
	 * This placeholder string will be replaced by the Tom Sawyer
	 * Visualization for Java licensing tier.
	 */
	public static final String TSVJ_TIER_PLACEHOLDER =
		"%TSVJ_TIER%";
	
	public static final String SM_SIMPLE_STATE = "SimpleState";
	public static final String SM_COMPOSITE_STATE = "CompositeState";
	public static final String SM_CONCURRENT_STATE = "ConcurrentState";
	public static final String SM_CALL_STATE_MODEL = "CallStateModel";
	public static final String SM_END_STATE = "EndState";
	public static final String SM_REGION_STATE = "Region";
	public static final String SM_TRANSITION = "Transition";
}