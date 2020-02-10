package com.tibco.cep.dashboard.plugin.beviews;

import com.tibco.cep.dashboard.config.PropertyKey;
import com.tibco.cep.dashboard.config.PropertyKey.DATA_TYPE;
import com.tibco.cep.dashboard.config.PropertyKeys;
import com.tibco.cep.dashboard.plugin.beviews.data.SOURCE_AGGREGATE_VISIBLE_FIELDS;

public interface BEViewsProperties extends PropertyKeys {

	//public final static PropertyKey DATASET_LIMIT_AS_TIME_THRESHOLD_KEY = new PropertyKey("timedatasetlimit","Defines the threshold beyond which a improperly configured data set is consider time based",DATA_TYPE.Long,1000L);

	public final static PropertyKey MAX_TIME_RESULTSET_FETCH_CNT_KEY = new PropertyKey("max.timeresultset.fetch.count","Defines the maximum amount of data row(s) that will be fetched in a time based data set",DATA_TYPE.Integer,1000);

	public final static PropertyKey MAX_COUNT_RESULTSET_FETCH_CNT_KEY = new PropertyKey("max.countresultset.fetch.count","Defines the maximum amount of data row(s) that will be fetched in a non-time regular based data set",DATA_TYPE.Integer,120);

	public final static PropertyKey CLIENT_DEBUG_ENABLED = new PropertyKey("client.debug.enabled","Enabled client tracing (on client machine)",PropertyKey.DATA_TYPE.Boolean,Boolean.FALSE);

	public final static PropertyKey CLIENT_STREAMING_RECONNECT_FREQUENCY = new PropertyKey("client.streaming.reconnect.frequency","The delay between streaming port reconnect attempts",PropertyKey.DATA_TYPE.Long,new Long(5000L));

	public final static PropertyKey CLIENT_STREAMING_RECONNECT_ATTEMPTS = new PropertyKey("client.streaming.reconnect.attempts","The number of reconnect attempts before giving up",PropertyKey.DATA_TYPE.Integer,new Integer(2));

	public final static PropertyKey DEMO_MODE = new PropertyKey("demomode","Indicates whether to run the system in demo mode",PropertyKey.DATA_TYPE.Boolean,Boolean.FALSE);

	public final static PropertyKey DEMO_MODE_TIME_INCREMENT = new PropertyKey("demomode.time.increment","Defines the increment to add to generated dates in design data",PropertyKey.DATA_TYPE.Long,new Long(86400000L));

	public final static PropertyKey DEMO_MODE_UPDATE_FREQUENCY = new PropertyKey("demomode.update.frequency","Defines the delay between two updates of a series",PropertyKey.DATA_TYPE.Long,new Long(5000L));

//	public final static PropertyKey TIME_SERIES_STANDARDIZATION_MODE = new PropertyKey("timeseries.standardization.mode","Defines the mode to use when crunching a time series axis",PropertyKey.DATA_TYPE.String,TIME_AXIS_STANDARDIZATION_MODE.DATASET.toString().toLowerCase());

	public final static PropertyKey TIME_SERIES_MAX_PLOTTABLE_POINTS = new PropertyKey("timeseries.max.plottable.points","Defines the max plottable points on a time series axis [comma-separated/each value for a colpans]",PropertyKey.DATA_TYPE.String,"10,30,60");

	public final static PropertyKey TIME_SERIES_VISIBLE_BUCKET_VALUES = new PropertyKey("timeseries.visible.bucket.values","Defines the which values from the bucketed time series should be shown in the chart [comma-separated]",PropertyKey.DATA_TYPE.String,"latest");

	public final static PropertyKey RANGE_CHART_INTERSECT_DATA_ENABLED = new PropertyKey(true, "range.intersect.data.enabled", "Determins whether to render only intersection of data in a range chart", PropertyKey.DATA_TYPE.Boolean, Boolean.TRUE);

	public final static PropertyKey DRILLDOWN_TABLE_PAGE_COUNT = new PropertyKey("drilldown.table.page.count", "Defines the number of rows displayed in a single pagination of a table", PropertyKey.DATA_TYPE.Integer, new Integer(25));

	public final static PropertyKey DRILLDOWN_TABLE_SHOW_ALL_LIMIT = new PropertyKey("drilldown.table.showall.limit", "Defines the limit for display pending rows in a table before 'Show All' is allowed", PropertyKey.DATA_TYPE.Integer, new Integer(400));

	public final static PropertyKey DRILLDOWN_TABLE_MAX_COUNT = new PropertyKey("drilldown.table.max.count", "Defines the maximum number of rows to display within a table", PropertyKey.DATA_TYPE.Integer, new Integer(500));

	public final static PropertyKey DRILLDOWN_PAGE_MAX_COUNT = new PropertyKey("drilldown.page.max.count", "Defines the maximum number of rows to be display on a page across all table(s)", PropertyKey.DATA_TYPE.Integer, new Integer(2000));

	public final static PropertyKey DRILLDOWN_PAGINATION_MODE = new PropertyKey("drilldown.table.pagination.mode", "Defines the pagination mode within a table", PropertyKey.DATA_TYPE.String, "append");

	public final static PropertyKey DRILLDOWN_ROW_COLLAPSE_POLICY = new PropertyKey("drilldown.row.collapse.policy", "Defines the policy to follow when a user collapses a row", PropertyKey.DATA_TYPE.String, "remove");

//	public final static PropertyKey DRILLDOWN_MENU_EXTENSION_THRESHOLD = new PropertyKey("drilldown.menu.extension.threshold", "Defines the threshold for menu items after which extended sub menu is shown", PropertyKey.DATA_TYPE.Integer, new Integer(15));

//	public final static PropertyKey DRILLDOWN_MENU_EXTENSION_COUNT = new PropertyKey("drilldown.menu.extension.count", "Defines the max number of menu items to show on a menu when a sub menu has been added", PropertyKey.DATA_TYPE.Integer, new Integer(12));

	public final static PropertyKey DRILLDOWN_TABLE_SORT_ORDER = new PropertyKey("drilldown.table.default.sort", "Defines the sort order to be used for when user clicks on a unsorted column in the tables", PropertyKey.DATA_TYPE.String, "descending");

	public final static PropertyKey UPDATES_BUFFER_SIZE = new PropertyKey("updates.buffer.capacity", "Defines the capacity of the buffer which holds incoming metric updates before they are processed", PropertyKey.DATA_TYPE.Integer, new Integer(1024));

	public final static PropertyKey QUERY_MANAGER_ENABLE = new PropertyKey("querymanager.enabled","Indicates whether to add query manager to the system automatically",PropertyKey.DATA_TYPE.Boolean,Boolean.TRUE);

	public final static PropertyKey QUERY_MANAGER_EXPORT_DEFAULT_TYPE = new PropertyKey("querymanager.export.default.type","Defines the default type of a query data export",PropertyKey.DATA_TYPE.String,new String[]{"csv","xml"}, 0);

	public final static PropertyKey QUERY_MANAGER_EXPORT_DEPTH = new PropertyKey("querymanager.export.depth","Defines the limit for how many levels deep to go in a query data export",PropertyKey.DATA_TYPE.Integer,new Integer(1));

	public final static PropertyKey QUERY_MANAGER_EXPORT_TYPE_COUNT = new PropertyKey("querymanager.export.type.count","Defines the number of rows to fetch per type in a query data export",PropertyKey.DATA_TYPE.Integer,new Integer(100));

	public final static PropertyKey QUERY_MANAGER_EXPORT_ALL_COUNT = new PropertyKey("querymanager.export.all.count","Defines the total number of rows to fetch across all types in a query data export",PropertyKey.DATA_TYPE.Integer,new Integer(500));

	public final static PropertyKey QUERY_MANAGER_EXPORT_INCLUDE_SYSTEM_FIELDS = new PropertyKey("querymanager.include.systemfields","Indicates whether to include system fields in a query data export",PropertyKey.DATA_TYPE.Boolean,Boolean.FALSE);

	public final static PropertyKey DECIMAL_FORMAT = new PropertyKey("decimalformat","The format to use for decimal values in drilldown page",PropertyKey.DATA_TYPE.String,null);

	public final static PropertyKey INTEGER_FORMAT = new PropertyKey("integerformat","The format to use for integer values in drilldown page",PropertyKey.DATA_TYPE.String,null);

	public final static PropertyKey FILTER_PARAMETER_DATASET_MAX_COUNT = new PropertyKey("filter.parameter.dataset.max.count", "Defines the maximum size for the dataset for possible values for a parameter in a filter editor", PropertyKey.DATA_TYPE.Integer, new Integer(2000));

	public final static PropertyKey AUTO_ADJUST_CATEGORY_AXIS_SORT = new PropertyKey("auto.adjust.category.sort.enabled","Indicates whether to auto adjust the category axis sort to ascending when neither the axis nor any of the series have sort",PropertyKey.DATA_TYPE.Boolean,Boolean.FALSE);

	//Added to fix BE-12080 by Anand 06/01/2011
	public final static PropertyKey DRILLDOWN_AGGREGATE_VISIBLE_COLUMNS = new PropertyKey("drilldown.sources.aggregate.visible.columntypes", "Defines which aggregate columns of the source(s) to be displayedin drilldown", PropertyKey.DATA_TYPE.String, SOURCE_AGGREGATE_VISIBLE_FIELDS.COMPUTED.toString().toLowerCase());

	public final static PropertyKey DRILLDOWN_AGGREGATE_SYS_DEPENDENT_FIELDS = new PropertyKey("drilldown.include.dependent.systemfields","Indicates whether to include dependent system fields",PropertyKey.DATA_TYPE.Boolean,Boolean.FALSE);

	public final static PropertyKey NULL_VALUE_DISPLAY_VALUE = new PropertyKey(true, "drilldown.null.display.value", "Defines the text to be shown for null values in drilldown page", PropertyKey.DATA_TYPE.String, "");

	public final static PropertyKey FORCE_CACHE_QUERY_MODE = new PropertyKey(true, "cache.query.enforce","Indicates whether to enforce using cache for queries even if backing store is enabled",PropertyKey.DATA_TYPE.Boolean,Boolean.FALSE);

	public final static PropertyKey CACHE_QUERY_BINDINGS_ENABLE = new PropertyKey(true, "cache.query.bindings.enabled","Indicates whether to use bindings for cache queries",PropertyKey.DATA_TYPE.Boolean,Boolean.FALSE);

	public final static PropertyKey FILER_EDITOR_DATASET_GENERATION_ENABLE = new PropertyKey(true, "filter.editor.contentassist.generation.enabled","Indicates whether to use enable filter editor data set generation",PropertyKey.DATA_TYPE.Boolean,Boolean.TRUE);

	public final static PropertyKey SEARCH_INCLUDE_SYSTEM_FIELDS = new PropertyKey("search.include.systemfields","Indicates whether to include system fields on the search page",PropertyKey.DATA_TYPE.Boolean,Boolean.FALSE);

}
