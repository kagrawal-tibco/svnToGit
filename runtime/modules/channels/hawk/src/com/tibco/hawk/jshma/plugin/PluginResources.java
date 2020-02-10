/*******************************************************************************
 * File:    PluginResources.java
 *
 * Purpose: Defines the base resource class for the plugin package. This class
 *          defines the resources for US English.
 *
 * Author:  T. Cha
 *
 *          Copyright (c) 2001, 2002 TIBCO Software Inc
 ******************************************************************************/
package com.tibco.hawk.jshma.plugin;

import java.util.Hashtable;
import java.util.Locale;
import java.util.ResourceBundle;

import COM.TIBCO.hawk.utilities.resources.ListResource;

/**
 * Defines the base resource class for the plugin package and contains resource
 * definitions for US English. This is the default (US English) set of resource
 * definitions used if the resource ID is not overridden by a derived, locale
 * specific resource class. The US English derived class, PluginResources_en_US,
 * is derived from this class with no overrides.
 *****************************************************************************/
public class PluginResources extends ListResource {
	private static Locale sDefaultLocale = Locale.getDefault();
	private static Hashtable sResources = new Hashtable();

	private ResourceBundle mBundle;
	private Object[][] mContents;

	// **************************************************************************
	// * Central location for defining resource ids and error codes to insure
	// * that they are unique. Each error code has an associated entry in the
	// * resources class which defines the associated error description string
	// * template. The associated error description is generated from the error
	// * code and an optional array of substitution arguments. Gaps in numbering
	// * are not an issue. Resource Ids and error codes must be unique.
	// **************************************************************************

	// Resource IDs
	// -------------------------------------------------------------------------
	public static final int AGENT_NAME = 101;
	public static final int MA_NAME = 102;

	// Column Label IDs
	// -------------------------------------------------------------------------
	public static final int ALL_AGENT_COL_NAMES = 201;
	public static final int ALL_CLUSTER_COL_NAMES = 202;
	public static final int ALERT_COL_NAMES = 203;
	public static final int ALERT_HIST_COL_NAMES = 204;
	public static final int EVENT_HIST_COL_NAMES = 205;
	public static final int AGENT_VIEW_COL_NAMES = 206;
	public static final int AGENT_INFO_COL_NAMES = 207;
	public static final int MA_VIEW_COL_NAMES = 208;
	public static final int METHOD_VIEW_COL_NAMES = 209;
	public static final int MA_TRACKER_FILTER_COL_NAMES = 210;
	public static final int RESPONSE_TIME_TRACKER_COL_NAMES = 211;
	public static final int SLIDING_WINDOW_COL_NAMES = 212;

	// Error IDs
	// -------------------------------------------------------------------------
	public static final int HAWK_METHOD_INVOKE_FAILED = 302;
	public static final int HAWK_METHOD_SUBSCRIPTION_FAILED = 303;
	public static final int HAWK_METHOD_SUBSCRIPTION_ERROR = 304;
	public static final int HAWK_METHOD_SUBSCRIPTION_ERROR_CLEARED = 305;
	public static final int HAWK_METHOD_SUBSCRIPTION_TERMINATED = 306;
	public static final int HAWK_METHOD_SUBSCRIPTION_SUCCESSFUL = 307;
	public static final int FILE_NOT_FOUND = 311;
	public static final int FILE_IO_ERROR = 313;
	public static final int TIMEOUT = 321;
	public static final int RV_ERROR = 331;
	public static final int INTERNAL_ERROR = 332;
	public static final int NO_COLUMN_NAMES = 341;
	public static final int PARAM_NOT_EXISTS = 342;
	public static final int EXTRACT_DATA_FAILED = 343;
	public static final int AGENT_NOT_DISCOVERED = 344;
	public static final int MA_NOT_EXISTS = 345;
	public static final int METHOD_NOT_EXISTS = 346;
	public static final int CLASS_NOT_FOUND = 347;
	public static final int FAIL_TO_DESCRIBE_MA = 348;
	public static final int FAIL_TO_GET_LEGAL_CHOICES = 349;
	public static final int FAIL_TO_GET_VALUE_CHOICES = 350;
	public static final int FAIL_TO_GET_PARAM_TYPES = 351;
	public static final int ACTION_METHOD_NOT_ALLOWED = 352;

	// **************************************************************************
	// Accessor Methods
	// **************************************************************************
	public Object[][] getContents() {
		return mContents;
	}

	public PluginResources(Locale locale) {
		super();
		mBundle = getResourceBundle(locale);

		// **************************************************************************
		// String Resources
		// **************************************************************************
		mContents = new Object[][] {

				// Do NOT translate the Resource ID and Column Label ID
				// Resource ID Resource Strings
				// -----------------------------------------
				// -------------------------------------------------------
				{ Integer.toString(AGENT_NAME), "AgentName" },
				{ Integer.toString(MA_NAME), "MicroAgentName" },

				// Column Label ID Resource Strings
				// -----------------------------------------
				// -------------------------------------------------------

				{ Integer.toString(ALL_AGENT_COL_NAMES),
						new String[] { "Name", "IP Address", "Cluster", "Architecture", "OS" } },
				{
						Integer.toString(ALL_CLUSTER_COL_NAMES),
						new String[] { "Cluster", "High Alert", "Medium Alert", "Low Alert", "No Alerts",
								"Unavailable", "Total" } },
				{ Integer.toString(ALERT_COL_NAMES),
						new String[] { "State", "TimeStamp", "RuleBase", "Source", "Text", "Properties", "ID" } },
				{
						Integer.toString(ALERT_HIST_COL_NAMES),
						new String[] { "AgentName", "MicroAgentName", "State", "TimeStamp", "RuleBase", "Source",
								"Text", "Properties", "ID" } },
				{
						Integer.toString(EVENT_HIST_COL_NAMES),
						new String[] { "TimeStamp", "Action", "AgentName", "MicroAgentName", "State", "RuleBase",
								"Source", "ID", "Text", "Properties" } },
				{ Integer.toString(AGENT_VIEW_COL_NAMES), new String[] { "Agent", "Highest Alert", "OS Name" } },
				{
						Integer.toString(AGENT_INFO_COL_NAMES),
						new String[] { "IP Address", "DNS Name", "Highest Alert State", "Start Time",
								"O.S. Architecture", "O.S. Name", "O.S. Version", "Loaded Rulebases(s)" } },
				{ Integer.toString(MA_VIEW_COL_NAMES),
						new String[] { "Display Name", "MicroAgent Name", "Instance Number" } },
				{ Integer.toString(METHOD_VIEW_COL_NAMES),
						new String[] { "Method Name", "Method Type", "Asyncronous", "Description" } },
				{
						Integer.toString(MA_TRACKER_FILTER_COL_NAMES),
						new String[] { "AgentName", "MicroAgentName", "Cluster", "OS Name", "OS Version",
								"Architecture" } },
				{
						Integer.toString(RESPONSE_TIME_TRACKER_COL_NAMES),
						new String[] { "Average (ms)", "Last (ms)", "Min (ms)", "Max (ms)", "Standard Deviation (ms)",
								"Complete Count", "Abort Count", "Out of Sequence Count", "Total Time(sec)",
								"Since Last Start (sec)", "Since Last Stop/Abort (sec)" } },
				{
						Integer.toString(SLIDING_WINDOW_COL_NAMES),
						new String[] { "Last Rate", "Min Rate", "Max Rate", "Rate Change", "Curr Slot Count",
								"Overall Count", "Overall Rate", "Last Weighed Rate", "Min Weighed Rate",
								"Max Weighed Rate", "Weighed Rate change", "Curr Slot Weight", "Overall Weight",
								"Overall Weighed Rate", "Overall Weight Average", "Overall Min Weight",
								"Overall Max Weight", "Overall Weight Standard Deviation" } },

				// Error ID Error Strings
				// ----------------------------------------------------------
				// -------------------------------------------------------
				{ Integer.toString(HAWK_METHOD_INVOKE_FAILED),
						mBundle.getString("JSHMA.HAWK_METHOD_INVOKE_FAILED.message") },

				{ Integer.toString(HAWK_METHOD_SUBSCRIPTION_FAILED),
						mBundle.getString("JSHMA.HAWK_METHOD_SUBSCRIPTION_FAILED.message") },

				{ Integer.toString(HAWK_METHOD_SUBSCRIPTION_ERROR),
						mBundle.getString("JSHMA.HAWK_METHOD_SUBSCRIPTION_ERROR.message") },

				{ Integer.toString(HAWK_METHOD_SUBSCRIPTION_ERROR_CLEARED),
						mBundle.getString("JSHMA.HAWK_METHOD_SUBSCRIPTION_ERROR_CLEARED.message") },

				{ Integer.toString(HAWK_METHOD_SUBSCRIPTION_TERMINATED),
						mBundle.getString("JSHMA.HAWK_METHOD_SUBSCRIPTION_TERMINATED.message") },

				{ Integer.toString(HAWK_METHOD_SUBSCRIPTION_SUCCESSFUL),
						mBundle.getString("JSHMA.HAWK_METHOD_SUBSCRIPTION_SUCCESSFUL.message") },

				{ Integer.toString(FILE_NOT_FOUND), mBundle.getString("JSHMA.FILE_NOT_FOUND.message") },

				{ Integer.toString(FILE_IO_ERROR), mBundle.getString("JSHMA.FILE_IO_ERROR.message") },

				{ Integer.toString(TIMEOUT), mBundle.getString("JSHMA.TIMEOUT.message") },

				{ Integer.toString(RV_ERROR), mBundle.getString("JSHMA.RV_ERROR.message") },

				{ Integer.toString(NO_COLUMN_NAMES), mBundle.getString("JSHMA.NO_COLUMN_NAMES.message") },

				{ Integer.toString(PARAM_NOT_EXISTS), mBundle.getString("JSHMA.PARAM_NOT_EXISTS.message") },

				{ Integer.toString(EXTRACT_DATA_FAILED), mBundle.getString("JSHMA.EXTRACT_DATA_FAILED.message") },

				{ Integer.toString(AGENT_NOT_DISCOVERED), mBundle.getString("JSHMA.AGENT_NOT_DISCOVERED.message") },

				{ Integer.toString(MA_NOT_EXISTS), mBundle.getString("JSHMA.MA_NOT_EXISTS.message") },

				{ Integer.toString(METHOD_NOT_EXISTS), mBundle.getString("JSHMA.METHOD_NOT_EXISTS.message") },

				{ Integer.toString(CLASS_NOT_FOUND), mBundle.getString("JSHMA.CLASS_NOT_FOUND.message") },

				{ Integer.toString(FAIL_TO_DESCRIBE_MA), mBundle.getString("JSHMA.FAIL_TO_DESCRIBE_MA.message") },

				{ Integer.toString(FAIL_TO_GET_LEGAL_CHOICES),
						mBundle.getString("JSHMA.FAIL_TO_GET_LEGAL_CHOICES.message") },

				{ Integer.toString(FAIL_TO_GET_VALUE_CHOICES),
						mBundle.getString("JSHMA.FAIL_TO_GET_VALUE_CHOICES.message") },

				{ Integer.toString(FAIL_TO_GET_PARAM_TYPES), mBundle.getString("JSHMA.FAIL_TO_GET_PARAM_TYPES.message") },

				{ Integer.toString(ACTION_METHOD_NOT_ALLOWED),
						mBundle.getString("JSHMA.ACTION_METHOD_NOT_ALLOWED.message") },

				{ Integer.toString(INTERNAL_ERROR), mBundle.getString("JSHMA.INTERNAL_ERROR.message") }

		};

	}

	public static void setDefaultLocale(Locale locale) {
		sDefaultLocale = locale;
	}

	public static Locale getDefaultLocale() {
		return sDefaultLocale;
	}

	public static ListResource getResources() {
		return getResources(sDefaultLocale);
	}

	public static ListResource getResources(Locale locale) {

		if (locale == null)
			locale = Locale.getDefault();

		ListResource bundle = (ListResource) sResources.get(locale);
		if (bundle == null) {
			bundle = new PluginResources(locale);
			// The content in that file will be based on locale
			sResources.put(locale, bundle);
		}

		return bundle;
	}

	public ResourceBundle getResourceBundle(Locale locale) {
		if (locale == null)
			locale = Locale.getDefault();

		ResourceBundle bundle = ResourceBundle.getBundle("JshmaResources", locale);

		return bundle;
	}

}
