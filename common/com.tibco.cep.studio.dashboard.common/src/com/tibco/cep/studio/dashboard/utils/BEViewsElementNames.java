package com.tibco.cep.studio.dashboard.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BEViewsElementNames {

	//generic types
	public static final String ALERT 							= "Alert";
	public static final String ALERT_ACTION 					= "AlertAction";

	public static final String SERIES_CONFIG 					= "SeriesConfig";
	public static final String VISUALIZATION 					= "Visualization";
	public static final String COMPONENT 						= "Component";
	public static final String RELATED_COMPONENT 				= "RelatedComponent";

	public static final String PAGE								= "Page";
	public static final String PARTITION 						= "Partition";
	public static final String PANEL 							= "Panel";

	//concrete types
	//metric
	public static final String METRIC 							= "Metric";

	//data source
	public static final String DATA_SOURCE              		= "DataSource";
	public static final String QUERY_PARAM 						= "QueryParam";

	//action rule
	public static final String ACTION_RULE 						= "ActionRule";
	public static final String RANGE_ALERT 						= "RangeAlert";
	public static final String VISUAL_ALERT_ACTION 				= "VisualAlertAction";

	//series config
	public static final String CHART_SERIES_CONFIG 				= "ChartSeriesConfig";
	public static final String RANGE_PLOT_CHART_SERIES_CONFIG 	= "RangePlotChartSeriesConfig";
	public static final String TEXT_SERIES_CONFIG 				= "TextSeriesConfig";
	public static final String STATE_SERIES_CONFIG 				= "StateSeriesConfig";

	//visualizations
	public static final String BAR_CHART_VISUALIZATION 			= "BarChartVisualization";
	public static final String PIE_CHART_VISUALIZATION 			= "PieChartVisualization";
	public static final String AREA_CHART_VISUALIZATION 		= "AreaChartVisualization";
	public static final String LINE_CHART_VISUALIZATION 		= "LineChartVisualization";
	public static final String SCATTER_CHART_VISUALIZATION 		= "ScatterPlotChartVisualization";
	public static final String RANGE_PLOT_CHART_VISUALIZATION 	= "RangePlotChartVisualization";
	public static final String TEXT_VISUALIZATION 				= "TextVisualization";
	public static final String STATE_VISUALIZATION 				= "StateVisualization";

	//components
	public static final String CHART_COMPONENT 					= "ChartComponent";
	public static final String TEXT_COMPONENT 					= "TextComponent";
	public static final String STATE_MACHINE_COMPONENT    		= "StateMachineComponent";
	public static final String STATE 							= "State";
	public static final String STATE_MACHINE 					= "StateMachine";
	public static final String PAGE_SELECTOR_COMPONENT 			= "PageSelectorComponent";
	public static final String QUERY_MANAGER_COMPONENT    		= "QueryManagerComponent";
	public static final String SEARCH_VIEW_COMPONENT      		= "SearchViewComponent";

	//page
	public static final String DASHBOARD_PAGE             		= "DashboardPage";
	public static final String SEARCH_PAGE                		= "SearchPage";
	public static final String RELATED_PAGE            			= "RelatedPage";

	//skin
	public static final String SKIN 							= "Skin";
	public static final String COMPONENT_COLOR_SET 				= "ComponentColorSet";
	public static final String DEFAULT_COMPONENT_COLOR_SET 		= "DefaultComponentColorSet";
	public static final String CHART_COLOR_SET            		= "ChartComponentColorSet";
	public static final String SERIES_COLOR               		= "SeriesColor";
	public static final String TEXT_COLOR_SET             		= "TextComponentColorSet";

	//view
	public static final String VIEW             				= "View";

	//role preference
	public static final String ROLE_PREFERENCE              	= "RolePreference";
	public static final String COMPONENT_GALLERY_FOLDER 		= "ComponentGalleryFolder";

	//independent
	public static final String HEADER             				= "Header";
	public static final String LOGIN              				= "Login";
	public static final String HEADER_LINK						= "HeaderLinks";

	//extensions
	public static final String EXT_METRIC 						= "metric";

	public static final String EXT_DATA_SOURCE              	= "datasource";

	public static final String EXT_CHART_COMPONENT 				= "chart";
	public static final String EXT_TEXT_COMPONENT 				= "chart";
	public static final String EXT_STATE_MACHINE_COMPONENT    	= "smcomponent";
	public static final String EXT_PAGE_SELECTOR_COMPONENT 		= "pageselector";
	public static final String EXT_QUERY_MANAGER_COMPONENT    	= "querymanager";
	public static final String EXT_SEARCH_VIEW_COMPONENT      	= "searchview";

	public static final String EXT_DASHBOARD_PAGE             	= "dashboardpage";
	public static final String EXT_SEARCH_PAGE                	= "searchpage";
	public static final String EXT_RELATED_PAGE            		= "relatedpage";

	public static final String EXT_SKIN              			= "skin";
	public static final String EXT_CHART_COLOR_SET            	= "chartcolorset";
	public static final String EXT_SERIES_COLOR               	= "seriescolor";
	public static final String EXT_TEXT_COLOR_SET             	= "textcolorset";

	public static final String EXT_VIEW             			= "view";

	public static final String EXT_ROLE_PREFERENCE             	= "rolepreference";

	public static final String EXT_HEADER             			= "header";
	public static final String EXT_LOGIN              			= "login";

	//psuedo type for unifying text and chart component
	public static final String TEXT_OR_CHART_COMPONENT 			= "ChartComponentORTextComponent";
	public static final String EXT_TEXT_OR_CHART_COMPONENT		= "chart";
	public static final String SYSTEM_ELEMENTS					= "System";
	public static final String EXT_SYSTEM_ELEMENTS				= "system";

	//additional psuedo type
	public static final String OTHER_COMPONENT 					= "OtherComponent";
	public static final String ALL_COMPONENT 					= "AllComponent";

	//maintains type to extension
	private static final Map<String, String> TYPE_TO_EXTENSIONS = new HashMap<String, String>();
	//maintains extension to type
	private static final Map<String, String> EXTENSION_TO_TYPES = new HashMap<String, String>();
	//maintains type hierarchy
	private static final Map<String, String> SUB_TO_SUPER_TYPES = new HashMap<String, String>();
	//maintains top level elements
	private static final List<String> TOP_LEVEL_ELEMENTS = new LinkedList<String>();

	static {
		//create the top level elements list
		TOP_LEVEL_ELEMENTS.add(METRIC);
		TOP_LEVEL_ELEMENTS.add(DATA_SOURCE);
		TOP_LEVEL_ELEMENTS.add(CHART_COMPONENT);
		TOP_LEVEL_ELEMENTS.add(TEXT_COMPONENT);
		TOP_LEVEL_ELEMENTS.add(STATE_MACHINE_COMPONENT);
		TOP_LEVEL_ELEMENTS.add(PAGE_SELECTOR_COMPONENT);
		TOP_LEVEL_ELEMENTS.add(QUERY_MANAGER_COMPONENT);
		TOP_LEVEL_ELEMENTS.add(SEARCH_VIEW_COMPONENT);
		TOP_LEVEL_ELEMENTS.add(DASHBOARD_PAGE);
		TOP_LEVEL_ELEMENTS.add(SEARCH_PAGE);
		TOP_LEVEL_ELEMENTS.add(RELATED_PAGE);
		TOP_LEVEL_ELEMENTS.add(SKIN);
		TOP_LEVEL_ELEMENTS.add(CHART_COLOR_SET);
		TOP_LEVEL_ELEMENTS.add(TEXT_COLOR_SET);
		TOP_LEVEL_ELEMENTS.add(SERIES_COLOR);
		TOP_LEVEL_ELEMENTS.add(VIEW);
		TOP_LEVEL_ELEMENTS.add(ROLE_PREFERENCE);
		TOP_LEVEL_ELEMENTS.add(HEADER);
		TOP_LEVEL_ELEMENTS.add(LOGIN);
		TOP_LEVEL_ELEMENTS.add(SYSTEM_ELEMENTS);

		//create type to extension mapping
		TYPE_TO_EXTENSIONS.put(METRIC, EXT_METRIC);
		TYPE_TO_EXTENSIONS.put(DATA_SOURCE, EXT_DATA_SOURCE);
		TYPE_TO_EXTENSIONS.put(CHART_COMPONENT, EXT_CHART_COMPONENT);
		TYPE_TO_EXTENSIONS.put(TEXT_COMPONENT, EXT_TEXT_COMPONENT);
		TYPE_TO_EXTENSIONS.put(STATE_MACHINE_COMPONENT, EXT_STATE_MACHINE_COMPONENT);
		TYPE_TO_EXTENSIONS.put(PAGE_SELECTOR_COMPONENT, EXT_PAGE_SELECTOR_COMPONENT);
		TYPE_TO_EXTENSIONS.put(QUERY_MANAGER_COMPONENT, EXT_QUERY_MANAGER_COMPONENT);
		TYPE_TO_EXTENSIONS.put(SEARCH_VIEW_COMPONENT, EXT_SEARCH_VIEW_COMPONENT);
		TYPE_TO_EXTENSIONS.put(DASHBOARD_PAGE, EXT_DASHBOARD_PAGE);
		TYPE_TO_EXTENSIONS.put(SEARCH_PAGE, EXT_SEARCH_PAGE);
		TYPE_TO_EXTENSIONS.put(RELATED_PAGE, EXT_RELATED_PAGE);
		TYPE_TO_EXTENSIONS.put(SKIN, EXT_SKIN);
		TYPE_TO_EXTENSIONS.put(CHART_COLOR_SET, EXT_CHART_COLOR_SET);
		TYPE_TO_EXTENSIONS.put(TEXT_COLOR_SET, EXT_TEXT_COLOR_SET);
		TYPE_TO_EXTENSIONS.put(SERIES_COLOR, EXT_SERIES_COLOR);
		TYPE_TO_EXTENSIONS.put(VIEW, EXT_VIEW);
		TYPE_TO_EXTENSIONS.put(ROLE_PREFERENCE, EXT_ROLE_PREFERENCE);
		TYPE_TO_EXTENSIONS.put(HEADER, EXT_HEADER);
		TYPE_TO_EXTENSIONS.put(LOGIN, EXT_LOGIN);
		//add psuedo type information
		TYPE_TO_EXTENSIONS.put(TEXT_OR_CHART_COMPONENT, EXT_TEXT_OR_CHART_COMPONENT);
		TYPE_TO_EXTENSIONS.put(SYSTEM_ELEMENTS, EXT_SYSTEM_ELEMENTS);

		//create extension to type mapping
		EXTENSION_TO_TYPES.put(EXT_METRIC, METRIC);
		EXTENSION_TO_TYPES.put(EXT_DATA_SOURCE, DATA_SOURCE);
		EXTENSION_TO_TYPES.put(EXT_CHART_COMPONENT, CHART_COMPONENT);
		EXTENSION_TO_TYPES.put(EXT_TEXT_COMPONENT, TEXT_COMPONENT);
		EXTENSION_TO_TYPES.put(EXT_STATE_MACHINE_COMPONENT, STATE_MACHINE_COMPONENT);
		EXTENSION_TO_TYPES.put(EXT_PAGE_SELECTOR_COMPONENT, PAGE_SELECTOR_COMPONENT);
		EXTENSION_TO_TYPES.put(EXT_QUERY_MANAGER_COMPONENT, QUERY_MANAGER_COMPONENT);
		EXTENSION_TO_TYPES.put(EXT_SEARCH_VIEW_COMPONENT, SEARCH_VIEW_COMPONENT);
		EXTENSION_TO_TYPES.put(EXT_DASHBOARD_PAGE, DASHBOARD_PAGE);
		EXTENSION_TO_TYPES.put(EXT_SEARCH_PAGE, SEARCH_PAGE);
		EXTENSION_TO_TYPES.put(EXT_RELATED_PAGE, RELATED_PAGE);
		EXTENSION_TO_TYPES.put(EXT_SKIN, SKIN);
		EXTENSION_TO_TYPES.put(EXT_CHART_COLOR_SET, CHART_COLOR_SET);
		EXTENSION_TO_TYPES.put(EXT_TEXT_COLOR_SET, TEXT_COLOR_SET);
		EXTENSION_TO_TYPES.put(EXT_SERIES_COLOR, SERIES_COLOR);
		EXTENSION_TO_TYPES.put(EXT_VIEW, VIEW);
		EXTENSION_TO_TYPES.put(EXT_ROLE_PREFERENCE, ROLE_PREFERENCE);
		EXTENSION_TO_TYPES.put(EXT_HEADER, HEADER);
		EXTENSION_TO_TYPES.put(EXT_LOGIN, LOGIN);
		//INFO add psuedo type information which over rides the information for chart/text component
		EXTENSION_TO_TYPES.put(EXT_TEXT_OR_CHART_COMPONENT, TEXT_OR_CHART_COMPONENT);
		EXTENSION_TO_TYPES.put(EXT_SYSTEM_ELEMENTS, SYSTEM_ELEMENTS);

		SUB_TO_SUPER_TYPES.put(DASHBOARD_PAGE, PAGE);
		SUB_TO_SUPER_TYPES.put(SEARCH_PAGE, PAGE);
		SUB_TO_SUPER_TYPES.put(RELATED_PAGE, PAGE);
	}

	/**
	 * Returns the list of top level types
	 * @return
	 */
	public static List<String> getTopLevelElementNames() {
		return TOP_LEVEL_ELEMENTS;
	}

	/**
	 * Returns the list of extensions for top level types
	 * @return
	 */
	public static Collection<String> getTopLevelElementExtensions() {
		return new HashSet<String>(TYPE_TO_EXTENSIONS.values());
	}

	/**
	 * Checks whether a type is top level or not
	 * @param type The type to be checked
	 * @return <code>true</code> if the type is top level else <code>false</code>
	 */
	public static boolean isTopLevelElement(String type) {
		return TOP_LEVEL_ELEMENTS.contains(type);
	}

	/**
	 * Returns the extension of a type
	 * @param type
	 * @return
	 */
	public static String getExtension(String type) {
		return TYPE_TO_EXTENSIONS.get(type);
	}

	/**
	 * Returns the type for a extension. Note that for {@link #E_CHART_COMPONENT} or {@link #E_TEXT_COMPONENT}, the type will be
	 * {@link #TEXT_OR_CHART_COMPONENT}
	 * @param extension
	 * @return
	 */

	public static String getType(String extension) {
		if (EXT_CHART_COMPONENT.equals(extension) == true) {
			return TEXT_OR_CHART_COMPONENT;
		}
		return EXTENSION_TO_TYPES.get(extension);
	}

	/**
	 * Checks if a type is equivalent to another type. The check is done over the entire type hierarchy
	 * @param type
	 * @param otherType
	 * @return
	 */
	public static boolean isTypeOf(String type, String otherType) {
		if (otherType.equals(type) == true) {
			return true;
		}
		if (TEXT_OR_CHART_COMPONENT.equals(type) == true) {
			if (TEXT_COMPONENT.equals(otherType) == true) {
				return true;
			}
			return CHART_COMPONENT.equals(otherType);
		}
		String superType = SUB_TO_SUPER_TYPES.get(type);
		return (superType != null && superType.equals(otherType));
	}

	/**
	 * Returns the super type of a type
	 * @param type
	 * @return
	 */
	public static String getSuperType(String type) {
		return SUB_TO_SUPER_TYPES.get(type);
	}

	/**
	 * @return
	 */
	public static List<String> getChartOrTextComponentTypes(){
		return Arrays.asList(BEViewsElementNames.TEXT_COMPONENT, BEViewsElementNames.CHART_COMPONENT);
	}

	public static List<String> getComponentTypes(){
		ArrayList<String> types = new ArrayList<String>(3);
		types.add(TEXT_COMPONENT);
		types.add(CHART_COMPONENT);
		types.add(STATE_MACHINE_COMPONENT);
		types.add(PAGE_SELECTOR_COMPONENT);
		types.add(QUERY_MANAGER_COMPONENT);
		types.add(SEARCH_VIEW_COMPONENT);
		types.add(RELATED_COMPONENT);
		return Collections.unmodifiableList(types);
	}

	//classifier types , may come in handy when we do concept visualization/customization
	public static final String CLASSIFIER_COMPONENT 			= "ClassifierComponent";
	public static final String CLASSIFIER_VISUALIZATION 		= "ClassifierVisualization";
	public static final String CLASSIFIER_SERIES_CONFIG 		= "ClassifierSeriesConfig";
	public static final String VALUE_DATA_CONFIG 				= "ValueDataConfig";

	//deprecated types
	public static final String ALERT_COMPONENT            		= "AlertComponent";
	public static final String ALERT_VISUALIZATION 				= "AlertVisualization";
	public static final String ALERT_SERIES_CONFIG 				= "AlertSeriesConfig";
	public static final String CONTEXT_ACTION_RULE_SET    		= "ContextActionRuleSet";
	public static final String RELATED_QUERY 					= "RelatedQuery";
	public static final String PAGESET_VISUALIZATION 			= "PageSetVisualization";
	public static final String GLOBAL_PAGESET_VISUALIZATION 	= "GlobalPageSetVisualization";
	public static final String PAGESET_SERIES_CONFIG 			= "PageSetSeriesConfig";
	public static final String TEXT_CHART_VISUALIZATION 		= "TextChartVisualization";
	public static final String TEXT_CHART_SERIES_CONFIG 		= "TextChartSeriesConfig";
	public static final String TEXT_VALUE_DATA_CONFIG 			= "TextValueDataConfig";
}
