package com.tibco.cep.studio.application.utils;

import org.eclipse.osgi.util.NLS;

/**
 * 
 * @author ggrigore
 * 
 */
public class Messages extends NLS {

	private static final String BUNDLE_NAME = "com.tibco.cep.studio.application.utils.messages"; //$NON-NLS-1$

	public static String BE_LICENSE;
	public static String Application_Info1;
	public static String Apps_New_Window_Action;

	public static String Apps_Max_Editor_Action;
	public static String Apps_Min_Editor_Action;
	public static String Apps_Activate_Editor_Action;
	public static String Apps_Next_Editor_Action;
	public static String Apps_Previous_Editor_Action;
	public static String Apps_Switch_Editor_Action;
	public static String Apps_Navigation_Action;
	public static String Apps_Menu_Window_ShowView;
	public static String Apps_Window_Title;
	public static String Apps_Window_Status_Line;

	public static String Apps_New_Action;
	public static String Apps_Open_Action;
	public static String Apps_Save_Action;
	public static String Apps_Save_As_Action;
	public static String Apps_Preferences_Action;

	public static String Apps_Menu_File;
	public static String Apps_Menu_Help;
	public static String Apps_Menu_Edit;
	public static String Apps_Menu_Navigate;
	public static String Apps_Menu_Window;
	public static String Apps_Menu_State_Machine;
	public static String Apps_Menu_Concept;
	public static String Apps_Menu_Event;
	public static String Apps_Coolbar_Main;

	public static String Apps_Menu_Diagram_GroupMarker;
	public static String Apps_Menu_Table_GroupMarker;
	
	public static String Apps_Show_Console_Action;
	public static String Apps_Show_Problems_Action;
	public static String Apps_Show_Palette_Action;
	public static String Apps_Show_Overview_Action;
	public static String Apps_Show_Project_Explorer_Action;
	public static String Apps_Key_Assist;

	public static String EXIT_TITLE;
	public static String EXIT_MESSAGE;

	public static String Help_Contents;
	public static String Help_Title;
	public static String Help_Error1;
	public static String Help_Error2;
	public static String Help_Error3;
	public static String Help_Error_Title;

	public static String Back;
	public static String Forward;
	public static String Stop;
	public static String Refresh;

	public static String Error_Title;
	public static String Error_Message;

	static {
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
