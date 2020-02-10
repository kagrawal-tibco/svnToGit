package com.tibco.cep.studio.debug.ui.prefs;

import com.tibco.cep.studio.debug.ui.IStudioDebugUIConstants;

public interface IStudioDebugPreferencesConstants {
	
	/**
	 * Boolean preference controlling whether to suspend
	 * execution when an uncaught Java exceptions encountered
	 * (while debugging).
	 */	
	public static final String PREF_SUSPEND_ON_UNCAUGHT_EXCEPTIONS= IStudioDebugUIConstants.PLUGIN_ID + ".javaDebug.SuspendOnUncaughtExceptions"; //$NON-NLS-1$
	
	/**
	 * Boolean preference controlling whether to suspend
	 * execution when a compilation error is encountered
	 * (while debugging).
	 */
	public static final String PREF_SUSPEND_ON_COMPILATION_ERRORS= IStudioDebugUIConstants.PLUGIN_ID + ".suspend_on_compilation_errors"; //$NON-NLS-1$
		
	/**
	 * Boolean preference controlling whether synthetic
	 * methods are to be filtered when stepping (and step
	 * filters are enabled).
	 */	
	public static final String PREF_FILTER_SYNTHETICS = IStudioDebugUIConstants.PLUGIN_ID + ".filter_synthetics"; //$NON-NLS-1$
	
	/**
	 * Boolean preference controlling whether static
	 * initializers are to be filtered when stepping (and step
	 * filters are enabled).
	 */		
	public static final String PREF_FILTER_STATIC_INITIALIZERS = IStudioDebugUIConstants.PLUGIN_ID + ".filter_statics"; //$NON-NLS-1$
	
	/**
	 * Boolean preference controlling whether constructors
	 * are to be filtered when stepping (and step
	 * filters are enabled).
	 */			
	public static final String PREF_FILTER_CONSTRUCTORS = IStudioDebugUIConstants.PLUGIN_ID + ".filter_constructors"; //$NON-NLS-1$
	
	/**
	 * Boolean preference controlling whether a step landing in a filtered
	 * location proceeds through to an un-filtered location, or returns.
	 * 
	 * @since 3.3
	 */
	public static final String PREF_STEP_THRU_FILTERS = IStudioDebugUIConstants.PLUGIN_ID + ".step_thru_filters"; //$NON-NLS-1$
	
	/**
	 * List of active step filters. A String containing a comma
	 * separated list of fully qualified type names/patterns.
	 */			
	public static final String PREF_ACTIVE_FILTERS_LIST = IStudioDebugUIConstants.PLUGIN_ID + ".active_filters"; //$NON-NLS-1$
	
	/**
	 * List of inactive step filters. A String containing a comma
	 * separated list of fully qualified type names/patterns.
	 */				
	public static final String PREF_INACTIVE_FILTERS_LIST = IStudioDebugUIConstants.PLUGIN_ID + ".inactive_filters"; //$NON-NLS-1$	
	
	/**
	 * Boolean preference controlling whether to alert
	 * with a dialog when hot code replace fails.
	 */	
	public static final String PREF_ALERT_HCR_FAILED = IStudioDebugUIConstants.PLUGIN_ID + ".javaDebug.alertHCRFailed"; //$NON-NLS-1$
	
	/**
	 * Boolean preference controlling whether to alert
	 * with a dialog when hot code replace is not supported.
	 */	
	public static final String PREF_ALERT_HCR_NOT_SUPPORTED = IStudioDebugUIConstants.PLUGIN_ID + ".javaDebug.alertHCRNotSupported"; //$NON-NLS-1$
	
	/**
	 * Boolean preference controlling whether to alert
	 * with a dialog when hot code replace results in 
	 * obsolete methods.
	 */	
	public static final String PREF_ALERT_OBSOLETE_METHODS = IStudioDebugUIConstants.PLUGIN_ID + "javaDebug.alertObsoleteMethods"; //$NON-NLS-1$
	
	/**
	 * Boolean preference controlling whether the debugger shows 
	 * qualifed names. When <code>true</code> the debugger
	 * will show qualified names in newly opened views.
	 * 
	 * @since 2.0
	 */
	public static final String PREF_SHOW_QUALIFIED_NAMES= IStudioDebugUIConstants.PLUGIN_ID + ".show_qualified_names"; //$NON-NLS-1$
	
	/**
	 * List of defined detail formatters.A String containing a comma
	 * separated list of fully qualified type names, the associated
	 * code snippet and an 'enabled' flag.
	 */
	public static final String PREF_DETAIL_FORMATTERS_LIST= IStudioDebugUIConstants.PLUGIN_ID + ".detail_formatters"; //$NON-NLS-1$
	
	/**
	 * Boolean preference indicating whether (non-final) static varibles should be shown
	 * in variable views. A view may over-ride this preference, and if so, stores
	 * its preference, prefixed by view id.
	 * 
	 * @since 2.1
	 */
	public static final String PREF_SHOW_STATIC_VARIABLES= IStudioDebugUIConstants.PLUGIN_ID + ".show_static_variables"; //$NON-NLS-1$
	
	/**
	 * Boolean preference indicating whether constant (final static) varibles should be shown
	 * in variable views. A view may over-ride this preference, and if so, stores
	 * its preference, prefixed by view id.
	 * 
	 * @since 2.1
	 */	
	public static final String PREF_SHOW_CONSTANTS= IStudioDebugUIConstants.PLUGIN_ID + ".show_constants"; //$NON-NLS-1$
	
	/**
	 * Boolean preference indicating whether null array entries should be shown
	 * in variable views. A view may over-ride this preference, and if so, stores
	 * its preference, prefixed by view id.
	 * 
	 * @since 3.0
	 */	
	public static final String PREF_SHOW_NULL_ARRAY_ENTRIES = IStudioDebugUIConstants.PLUGIN_ID + ".show_null_entries"; //$NON-NLS-1$	
	
	/**
	 * Boolean preference indicating whether hex values should be shown for primitives
	 * in variable views. A view may over-ride this preference, and if so, stores
	 * its preference, prefixed by view id.
	 * 
	 * @since 2.1
	 */	
	public static final String PREF_SHOW_HEX = IStudioDebugUIConstants.PLUGIN_ID + ".show_hex"; //$NON-NLS-1$	
	
	/**
	 * Boolean preference indicating whether char values should be shown for primitives
	 * in variable views. A view may over-ride this preference, and if so, stores
	 * its preference, prefixed by view id.
	 * 
	 * @since 2.1
	 */	
	public static final String PREF_SHOW_CHAR = IStudioDebugUIConstants.PLUGIN_ID + ".show_char"; //$NON-NLS-1$
	
	/**
	 * Boolean preference indicating whether unsigned values should be shown for primitives
	 * in variable views. A view may over-ride this preference, and if so, stores
	 * its preference, prefixed by view id.
	 * 
	 * @since 2.1
	 */	
	public static final String PREF_SHOW_UNSIGNED = IStudioDebugUIConstants.PLUGIN_ID + ".show_unsigned"; //$NON-NLS-1$	
	
	/**
	 * String preference indication when and where variable details should appear.
	 * Valid values include: 
	 * <ul>
	 *   <li><code>INLINE_ALL</code> to show inline details for all variables
	 *   <li><code>INLINE_FORMATTERS</code> to show inline details for variables with formatters
	 *   <li><code>DETAIL_PANE</code> to show details only in the detail pane
	 * </ul> 
	 */
	public static final String PREF_SHOW_DETAILS = IStudioDebugUIConstants.PLUGIN_ID + ".show_details"; //$NON-NLS-1$
	/**
	 * "Show detail" preference values.
	 */
	public static final String INLINE_ALL="INLINE_ALL"; //$NON-NLS-1$
	public static final String INLINE_FORMATTERS="INLINE_FORMATTERS"; //$NON-NLS-1$
	public static final String DETAIL_PANE="DETAIL_PANE"; //$NON-NLS-1$
	
	/**
	 * Common dialog settings
	 */
	public static final String DIALOG_ORIGIN_X = "DIALOG_ORIGIN_X"; //$NON-NLS-1$
	public static final String DIALOG_ORIGIN_Y = "DIALOG_ORIGIN_Y"; //$NON-NLS-1$
	public static final String DIALOG_WIDTH = "DIALOG_WIDTH"; //$NON-NLS-1$
	public static final String DIALOG_HEIGHT = "DIALOG_HEIGHT"; //$NON-NLS-1$	


	/**
	 * Boolean preference controlling whether to alert
	 * with a dialog when unable to install a breakpoint
	 * (line info not available, ...)
	 */	
	public static final String PREF_ALERT_UNABLE_TO_INSTALL_BREAKPOINT = IStudioDebugUIConstants.PLUGIN_ID + ".prompt_unable_to_install_breakpoint"; //$NON-NLS-1$
	
	public static final String PREF_THREAD_MONITOR_IN_DEADLOCK_COLOR= "org.eclipse.jdt.debug.ui.InDeadlockColor"; //$NON-NLS-1$

	/**
	 * @since 3.2
	 */
	public static final String PREF_OPEN_INSPECT_POPUP_ON_EXCEPTION = IStudioDebugUIConstants.PLUGIN_ID + ".open_inspect_popup_on_exception"; //$NON-NLS-1$

	/**
	 * Boolean  preference controlling whether the java stack trace
	 * console should be formatted when ever a paste occurs.
	 * @since 3.3
	 */
	public static final String PREF_AUTO_FORMAT_JSTCONSOLE = IStudioDebugUIConstants.PLUGIN_ID + ".auto_format_jstconsole"; //$NON-NLS-1$;

}
