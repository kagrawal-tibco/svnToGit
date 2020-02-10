package com.tibco.cep.studio.ui.palette.utils;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {

	private static final String BUNDLE_NAME = "com.tibco.cep.studio.ui.palette.utils.messages";
	
	public static String NONE;
	public static String LINE;
	public static String POINT;
	public static String GRID;
	
	public static String PALLETTE_DRAWER_GLOBAL_TOOL_TITLE;
	public static String PALLETTE_DRAWER_GLOBAL_TOOL_TOOLTIP;
	public static String PALLETTE_ENTRY_TOOL_SELECT_TITLE;
	public static String PALLETTE_ENTRY_TOOL_SELECT_TOOLTIP;
	public static String PALLETTE_ENTRY_TOOL_PAN_TITLE;
	public static String PALLETTE_ENTRY_TOOL_PAN_TOOLTIP;
	public static String PALLETTE_ENTRY_TOOL_ZOOM_TITLE;
	public static String PALLETTE_ENTRY_TOOL_ZOOM_TOOLTIP;
	public static String PALLETTE_ENTRY_TOOL_INTERACTIVE_ZOOM_TITLE;
	public static String PALLETTE_ENTRY_TOOL_INTERACTIVE_ZOOM_TOOLTIP;
	public static String PALLETTE_ENTRY_TOOL_LINK_NAVIGATOR_TITLE;
	public static String PALLETTE_ENTRY_TOOL_LINK_NAVIGATOR_TOOLTIP;
	public static String PALLETTE_ENTRY_TOOL_NOTE_TITLE;
	public static String PALLETTE_ENTRY_TOOL_NOTE_TOOLTIP;
	
	static {
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
