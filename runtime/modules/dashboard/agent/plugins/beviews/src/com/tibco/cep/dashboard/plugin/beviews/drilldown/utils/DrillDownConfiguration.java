package com.tibco.cep.dashboard.plugin.beviews.drilldown.utils;

import java.util.Properties;

import com.tibco.cep.dashboard.plugin.beviews.BEViewsProperties;

public class DrillDownConfiguration {
	
	private static enum PAGINATION_MODE { append, replace };
	
	private static enum SORT_ORDER { ascending, descending };
	
	private static enum COLLAPSE_POLCIY { remove, retain };
	
	private static int pageSize;
	
	private static int showAllThreshold;
	
	private static int maxTableCount;
	
	private static int maxPageCount;
	
	private static SORT_ORDER defaultSortOrder;
	
	private static PAGINATION_MODE paginationMode;
	
	private static COLLAPSE_POLCIY rowCollapsePolicy;
	
	public static void init(Properties properties){
		pageSize = (Integer)BEViewsProperties.DRILLDOWN_TABLE_PAGE_COUNT.getValue(properties);
		showAllThreshold = (Integer)BEViewsProperties.DRILLDOWN_TABLE_SHOW_ALL_LIMIT.getValue(properties);
		maxTableCount = (Integer)BEViewsProperties.DRILLDOWN_TABLE_MAX_COUNT.getValue(properties);
		maxPageCount = (Integer)BEViewsProperties.DRILLDOWN_PAGE_MAX_COUNT.getValue(properties);
		defaultSortOrder = SORT_ORDER.valueOf(String.valueOf(BEViewsProperties.DRILLDOWN_TABLE_SORT_ORDER.getValue(properties)).toLowerCase());
		paginationMode = PAGINATION_MODE.valueOf(String.valueOf(BEViewsProperties.DRILLDOWN_PAGINATION_MODE.getValue(properties)).toLowerCase());
		rowCollapsePolicy = COLLAPSE_POLCIY.valueOf(String.valueOf(BEViewsProperties.DRILLDOWN_ROW_COLLAPSE_POLICY.getValue(properties)).toLowerCase());
	}

	/**
	 * Returns the number of rows to show in a single page of a table
	 * @return
	 */
	public static int getTablePageSize() {
		return pageSize;
	}

	/**
	 * Returns the threshold of display pending rows in a table before the 'show all' in enabled 
	 * @return
	 * @deprecated although passed as a argument to the Javascript function 'updateFooterInAppendMode' but not used 
	 */
	public static int getTableShowAllThreshold() {
		return showAllThreshold;
	}
	
	/**
	 * Returns the total number of rows that can be displayed in a table
	 * @return
	 */
	public static int getMaxTableCount() {
		return maxTableCount;
	}

	/**
	 * Returns the total number of rows that can be displayed on a single page
	 * @return
	 */
	public static int getMaxPageCount() {
		return maxPageCount;
	}
	
	/**
	 * Returns whether to use ascending/descending as the default sort order
	 * @return <code>true</code> for ascending else <code>false</code>
	 */
	public static boolean isDefaultSortOrderAscending(){
		return defaultSortOrder.compareTo(SORT_ORDER.ascending) == 0;
	}

	/**
	 * Returns whether the pagination mode is append/replace
	 * @return <code>true</code> for append else <code>false</code>
	 */
	public static boolean isPaginationInAppendMode() {
		return paginationMode.compareTo(PAGINATION_MODE.append) == 0;
	}

	/**
	 * Returns whether to remove or retain data when a node is collapsed
	 * @return <code>true</code> for remove else <code>false</code>
	 */	
	public static boolean isRemoveOnCollapse() {
		return rowCollapsePolicy.compareTo(COLLAPSE_POLCIY.remove) == 0;
	}
	
}