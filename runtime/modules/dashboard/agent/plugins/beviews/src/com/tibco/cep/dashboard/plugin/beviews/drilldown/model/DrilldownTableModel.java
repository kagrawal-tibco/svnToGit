package com.tibco.cep.dashboard.plugin.beviews.drilldown.model;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.tibco.cep.dashboard.common.data.BuiltInTypes;
import com.tibco.cep.dashboard.common.data.DataType;
import com.tibco.cep.dashboard.common.data.FieldValue;
import com.tibco.cep.dashboard.common.data.Tuple;
import com.tibco.cep.dashboard.common.data.TupleSchemaField;
import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.config.ConfigurationProperties;
import com.tibco.cep.dashboard.management.ExceptionHandler;
import com.tibco.cep.dashboard.management.MessageGenerator;
import com.tibco.cep.dashboard.plugin.beviews.BEViewsProperties;
import com.tibco.cep.dashboard.plugin.beviews.data.TupleSchemaFactory;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.DrilldownProvider;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QuerySpec;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.renderers.DrilldownTableCellRenderer;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.integration.DefaultTableCellModel;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.integration.RequestTableModel;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.integration.TableCellModel;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.integration.TableMenu;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.integration.TableModel;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.integration.TableModelException;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.model.TableCellRenderer;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.model.TableTreeConstants;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.popupmenu.model.MenuProperty;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.util.HREFLink;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.utils.DrillDownConfiguration;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.utils.DrilldownParameterNames;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.utils.DrilldownRequestStore;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.visengine.EntityVisualizerProvider;
import com.tibco.cep.dashboard.plugin.beviews.mal.EntityCache;
import com.tibco.cep.dashboard.plugin.beviews.mal.MALExternalURLUtils;
import com.tibco.cep.dashboard.plugin.beviews.search.BizSessionSearchStore;
import com.tibco.cep.dashboard.psvr.biz.BizSession;
import com.tibco.cep.dashboard.psvr.biz.BizSessionRequest;
import com.tibco.cep.dashboard.psvr.biz.KnownParameterNames;
import com.tibco.cep.dashboard.psvr.common.FatalException;
import com.tibco.cep.dashboard.psvr.common.query.QueryException;
import com.tibco.cep.dashboard.psvr.data.DataException;
import com.tibco.cep.dashboard.psvr.mal.ElementNotFoundException;
import com.tibco.cep.dashboard.psvr.variables.VariableContext;
import com.tibco.cep.dashboard.psvr.variables.VariableInterpreter;
import com.tibco.cep.dashboard.security.SecurityToken;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.element.Metric;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

/**
 * @author rajesh
 *
 */
public class DrilldownTableModel extends RequestTableModel {

	private static final String EMPTY_STRING = "";

	private static final String TABLE_TREE_WIDGET_ID = "events";

	private static final String TABLE_BK_COLOR = "#FFFFFF";

	private static final String NO_ROWS_SELECTED_TEXT = "No rows selected.";

	private Logger logger;

	private ExceptionHandler exceptionHandler;

	private MessageGenerator messageGenerator;

	private String headerTitle;

	private int rowCount;

	private int columnCount;

	private Map<String, String> coreFldNamesToCustomFldNamesMap;

	private List<String> customFieldNames;

	private List<String> coreFieldNames;

	private List<Tuple> dataList;

	private String typeid;

	private TableCellModel[][] cellValues;

	private Map<String, Object> mapTableParameters = null;

	private List<NextInLine> lstNextInlines;

	private String groupByField;

	private Object groupByValue;

	private Map<String, String> mapFilterFields;

	private int totalRowCount;

	private String iteratorId;

	private int fetchCount;

	private boolean sortRequired = true;

	private boolean groupByRequired = true;

	private int[] displaySpan;

	private boolean bPaginationAppended = true;

	private String timeFilterMetricText = null;

	private Properties properties;

	private SecurityToken securityToken;

	// private String type;

	public DrilldownTableModel(SecurityToken securityToken, Properties properties, Logger logger, ExceptionHandler exceptionHandler, MessageGenerator messageGenerator, BizSessionRequest request, NextInLine nextInline)
			throws Exception {
		this(securityToken, properties, logger, exceptionHandler, messageGenerator, request);
		processNextInline(nextInline);
	}

	public DrilldownTableModel(SecurityToken securityToken, Properties properties, Logger logger, ExceptionHandler exceptionHandler, MessageGenerator messageGenerator, BizSessionRequest request) {
		super(request);
		this.securityToken = securityToken;
		this.properties = properties;
		this.logger = logger;
		this.exceptionHandler = exceptionHandler;
		this.messageGenerator = messageGenerator;
		init();
	}

	private void init() {
		String parentTypeID = HREFLink.getDecodedParameterValue(request, DrilldownParameterNames.PARENT_TYPE_ID);
		String parentInstanceID = HREFLink.getDecodedParameterValue(request, DrilldownParameterNames.PARENT_INSTANCE_ID);
		mapTableParameters = new HashMap<String, Object>();
		// mapTableParameters.put(DrilldownParameterNames.ETYPE, type);
		mapTableParameters.put(DrilldownParameterNames.TYPE_ID, typeid);
		if (parentTypeID != null) {
			mapTableParameters.put(DrilldownParameterNames.PARENT_TYPE_ID, parentTypeID);
			mapTableParameters.put(DrilldownParameterNames.PARENT_INSTANCE_ID, parentInstanceID);
		}
		if (iteratorId != null) {
			mapTableParameters.put(DrilldownParameterNames.FETCH_COUNT, String.valueOf(fetchCount));
			mapTableParameters.put(DrilldownParameterNames.ITERATOR_ID, iteratorId);
		}
		mapTableParameters.putAll(getAdditionalParameters(request));
		try {
			columnCount = Integer.parseInt(request.getParameter(TableTreeConstants.KEY_COLUMN_COUNT));
		} catch (IllegalArgumentException e) {
			columnCount = 0;
		}
	}

	/**
	 * @param nextInLine
	 * @throws Exception
	 */
	private void processNextInline(NextInLine nextInLine) throws Exception {
		typeid = nextInLine.getTypeID();
		groupByValue = nextInLine.getGroupFieldValue();
		headerTitle = nextInLine.getName();
		mapTableParameters = nextInLine.getQueryParameters();
		// mapTableParameters.put("iteratorid", iteratorId);
		fetchCount = nextInLine.getCount();

		totalRowCount = fetchCount;
		rowCount = nextInLine.getCount();

		// totalRowCount = Math.min(DrilldownUtils.getMaxRowsAllowed(), fetchCount);
		// rowCount = Math.min(DrilldownUtils.getMaxRowsAllowed(), rowCount);

		limitRowCounts(totalRowCount, rowCount);

		// if (nextInLine.getParentTypeID() == null) {
		// coreFldNamesToCustomFldNamesMap = EntityUtils.getDisplayableFields(request, nextInLine.getTypeID());// getDisplayableFields();
		// } else {
		// coreFldNamesToCustomFldNamesMap = EntityUtils.getDisplayableFields(request, nextInLine.getParentTypeID());// getDisplayableFields();
		// }

		if (nextInLine.getTypeID() != null) {
			coreFldNamesToCustomFldNamesMap = getDisplayableFields(nextInLine.getTypeID());
		} else {
			coreFldNamesToCustomFldNamesMap = getDisplayableFields(nextInLine.getParentTypeID());
		}

		columnCount = coreFldNamesToCustomFldNamesMap.size();
		customFieldNames = new ArrayList<String>(coreFldNamesToCustomFldNamesMap.values());
		// Calculate the value of metric level time filter
		// processTimeFilterMetricText(typeid);
	}

	@Override
	public int getColumnCount() throws TableModelException {
		return columnCount;
	}

	@Override
	public List<String> getColumnNames() throws TableModelException {
		return customFieldNames;
	}

	@Override
	public Map<String, String> getColumnFields() throws TableModelException {
		return coreFldNamesToCustomFldNamesMap;
	}

	@Override
	public int getRowCount() throws TableModelException {
		return rowCount;
	}

	@Override
	public TableModel[] getNestedTables() throws TableModelException {
		if (lstNextInlines == null || lstNextInlines.size() == 0) {
			return new DrilldownTableModel[0];
		}
		DrilldownTableModel[] nestedTableModels = new DrilldownTableModel[lstNextInlines.size()];
		Iterator<NextInLine> itNextInlines = lstNextInlines.iterator();
		for (int i = 0; itNextInlines.hasNext(); i++) {
			NextInLine nextInLine = itNextInlines.next();
			try {
				nestedTableModels[i] = new DrilldownTableModel(securityToken, properties, logger, exceptionHandler, messageGenerator, request, nextInLine);
				nestedTableModels[i].groupByField = groupByField;
				nestedTableModels[i].mapFilterFields = mapFilterFields;
				// if (nextInLine.getIteratorId() != null)
				// {
				nestedTableModels[i].mapTableParameters.put("fetchcount", nextInLine.getCount());
				// }
				nestedTableModels[i].iteratorId = nextInLine.getIteratorId();
				nestedTableModels[i].sortRequired = this.sortRequired;
				nestedTableModels[i].groupByRequired = this.groupByRequired;
			} catch (ElementNotFoundException e) {
				throw new TableModelException("Failed to load nested table data", e);
			} catch (FatalException e) {
				throw new TableModelException("Failed to load nested table data", e);
			} catch (Exception e) {
				throw new TableModelException("Failed to load nested table data", e);
			}
		}
		return nestedTableModels;
	}

	@Override
	public String getBackgroundColor() throws TableModelException {
		return TABLE_BK_COLOR;
	}

	@Override
	public Map<String, Object> getParameters() throws TableModelException {
		return mapTableParameters;
	}

	@Override
	public Map<String, Object> getNestedTableParam(int rowIndex) throws TableModelException {
		Tuple dataTuple = dataList.get(rowIndex);
		String instanceId = String.valueOf(dataTuple.getId());
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("instanceid", instanceId);
		return mapParameters;
	}

	@Override
	public Map<String, Object> getColumnSortParameters(int colIndex) {
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.putAll(mapTableParameters);
		mapParameters.put("sortfield", coreFieldNames.get(colIndex));
		return mapParameters;
	}

	@Override
	public Map<String, String> getAdditionalRowParameters(int rowIndex) throws TableModelException {
		Tuple dataTuple = dataList.get(rowIndex);
		String instanceId = String.valueOf(dataTuple.getId());
		Map<String, String> mapParameters = new HashMap<String, String>();
		// mapParameters.put(DrilldownParameterNames.ETYPE, type);
		mapParameters.put(DrilldownParameterNames.TYPE_ID, typeid);
		mapParameters.put(DrilldownParameterNames.INSTANCE_ID, instanceId);
		if (iteratorId != null) {
			mapParameters.put(DrilldownParameterNames.ITERATOR_ID, iteratorId);
		}
		return mapParameters;
	}

	@Override
	public TableCellModel getCellValueAt(int rowIndex, int colIndex) throws TableModelException {
		if (cellValues == null) {
			try {
				loadCellValues();
			} catch (Exception e) {
				throw new TableModelException("Failed to load the cell values", e);
			}
		}
		return cellValues[rowIndex][colIndex];
	}

	@Override
	public String getHeaderTitle() throws TableModelException {
		if (getRowCount() == 0) {
			return NO_ROWS_SELECTED_TEXT;
		}
		// return headerTitle + " (" + (1 + (int)(Math.random() *
		// getRowCount())) + ")";
		String title = EMPTY_STRING;
		if (groupByField != null) {
			title += coreFldNamesToCustomFldNamesMap.get(groupByField) + ": ";
		}
		// Anand - 05/09/2010 - Modified to use fetch count instead of total row count to show user how much data is in the db [1-ATA5DU]
		title += headerTitle + " (" + fetchCount + ")";
		// title += headerTitle + " (" + getTotalRowCount() + ")";
		return title;
	}

	@Override
	public TableMenu getHeaderMenu(String menuId) throws TableModelException {
		String headerMenuId = TABLE_TREE_WIDGET_ID + "_" + "headermenu";
		TableMenu tableMenu = new TableMenu(headerMenuId, "headermenu", "Events", true);
		tableMenu.setShowMenuFunctionName("showHeaderMenu");
		/*
		 * HREFLink link = new HREFLink(request.getContextPath(), QUERY_PAGELET_URL); link.addParameter("ssid", request.getParameter("ssid")); link.addParameter(DrilldownParameterNames.ETYPEID, typeid);
		 * tableMenu.addShowMenuParameters(link.toString());
		 */
		return tableMenu;
	}

	private void loadCellValues() throws TableModelException {
		cellValues = new TableCellModel[getRowCount()][getColumnCount()];
		Entity repositoryElement = EntityCache.getInstance().getEntity(typeid);
		Iterator<Tuple> rowIterator = dataList.iterator();
		int rowIndex = 0;
		while (rowIterator.hasNext() && rowIndex < rowCount) {
			Tuple rowTuple = rowIterator.next();
			Iterator<String> userfieldIterator = coreFieldNames.iterator();
			int colIndex = 0;
			while (userfieldIterator.hasNext()) {
				String fieldName = userfieldIterator.next();
				// Need to use the MDClassifier here
				String displayValue = getDisplayValue(request.getSession(), repositoryElement, fieldName, rowTuple);
				String externalURL = MALExternalURLUtils.getURLLink(repositoryElement.getGUID(), fieldName);
				if (externalURL != null) {
					try {
						VariableContext variableContext = new VariableContext(logger, securityToken, properties, rowTuple);
						externalURL = VariableInterpreter.getInstance().interpret(externalURL, variableContext, true);
					} catch (ParseException e) {
						logger.log(Level.WARN, "could not parse " + externalURL, e);
						externalURL = null;
					}
				}
				FieldValue fieldValue = rowTuple.getFieldValueByName(fieldName);
				String tooltip = displayValue;
				if (fieldValue.isNull() == true) {
					tooltip = "NULL";
				}
				cellValues[rowIndex][colIndex] = convertToTableCellModel(fieldValue, displayValue, tooltip, externalURL);
				colIndex++;
			}
			rowIndex++;
		}
	}

	private String getDisplayValue(BizSession session, Entity repositoryElement, String fieldName, Tuple rowTuple) {
		FieldValue fieldValue = rowTuple.getFieldValueByName(fieldName);
		if (fieldValue.isNull() == true) {
			return EMPTY_STRING;
		}
		DataType dataType = fieldValue.getDataType();
		if (BuiltInTypes.STRING.equals(dataType) == true) {
			return fieldValue.toString();
		}
		if (BuiltInTypes.BOOLEAN.equals(dataType) == true) {
			return fieldValue.toString();
		}
		if (BuiltInTypes.DATETIME.equals(dataType) == true) {
			return fieldValue.toString();
		}
		if (BuiltInTypes.DOUBLE.equals(dataType) == true) {
			String pattern = (String) BEViewsProperties.DECIMAL_FORMAT.getValue(properties);
			if (StringUtil.isEmptyOrBlank(pattern) == true) {
				NumberFormat format = NumberFormat.getInstance();
				format.setMaximumFractionDigits(2);
				format.setMinimumFractionDigits(2);
				return format.format((Double) fieldValue.getValue());
			}
			MessageFormat format = new MessageFormat("{0,number," + pattern + "}");
			return format.format(new Object[] { (Double) fieldValue.getValue() });
		}
		if (BuiltInTypes.FLOAT.equals(dataType) == true) {
			String pattern = (String) BEViewsProperties.DECIMAL_FORMAT.getValue(properties);
			if (StringUtil.isEmptyOrBlank(pattern) == true) {
				NumberFormat format = NumberFormat.getInstance();
				format.setMaximumFractionDigits(2);
				format.setMinimumFractionDigits(2);
				return format.format((Float) fieldValue.getValue());
			}
			MessageFormat format = new MessageFormat("{0,number," + pattern + "}");
			return format.format(new Object[] { (Float) fieldValue.getValue() });
		}
		if (BuiltInTypes.LONG.equals(dataType) == true) {
			String pattern = (String) BEViewsProperties.INTEGER_FORMAT.getValue(properties);
			if (StringUtil.isEmptyOrBlank(pattern) == true) {
				NumberFormat format = NumberFormat.getInstance();
				return format.format((Long) fieldValue.getValue());
			}
			MessageFormat format = new MessageFormat("{0,number," + pattern + "}");
			return format.format(new Object[] { (Long) fieldValue.getValue() });
		}
		if (BuiltInTypes.INTEGER.equals(dataType) == true) {
			String pattern = (String) BEViewsProperties.INTEGER_FORMAT.getValue(properties);
			if (StringUtil.isEmptyOrBlank(pattern) == true) {
				NumberFormat format = NumberFormat.getInstance();
				return format.format((Integer) fieldValue.getValue());
			}
			MessageFormat format = new MessageFormat("{0,number," + pattern + "}");
			return format.format(new Object[] { (Integer) fieldValue.getValue() });
		}
		return fieldValue.toString();
	}

	private TableCellModel convertToTableCellModel(FieldValue actualFieldValue, String displayValue, String toolTip, String drillableLink) {
		// Object actualValue = actualFieldValue.isNull() ? null : actualFieldValue.getValue();
		return new DefaultTableCellModel(actualFieldValue, displayValue, toolTip, drillableLink);
	}

	public void loadNestedTableData() throws TableModelException {
		String parentTypeId = request.getParameter(DrilldownParameterNames.TYPE_ID);
		String instanceid = request.getParameter("instanceid");

		try {
			BizSession session = request.getSession();
			lstNextInlines = DrilldownProvider.getInstance(session).getNextInLines(parentTypeId, instanceid, getAdditionalParameters(request));
		} catch (QueryException e) {
			throw new TableModelException(e.getMessage(), e);
		} catch (Exception e) {
			throw new TableModelException("Failed to load the nested Table Data", e);
		}
		return;
	}

	/**
	 * Returns all the additional parameters in the <code>request</code>
	 *
	 * @param request
	 *            The <code>EnhancedXMLRequest</code> from which to pick up the additional parameters
	 * @return <code>Map</code> containing <b>field name </b> as <b>key </b> and <br>
	 *         String </b> array as <b>value </b>
	 */
	private Map<String, String> getAdditionalParameters(BizSessionRequest request) {
		Map<String, String> additionalParams = new HashMap<String, String>();
		Iterator<String> parameterNames = request.getParameterNames();
		while (parameterNames.hasNext()) {
			String name = parameterNames.next();
			if (NextInLine.isAdditionalParameterName(name)) {
				additionalParams.put(NextInLine.unescapeAdditionalParameterName(name), request.getParameter(name));
			}
		}
		return additionalParams;
	}

	public void loadGroupTableData(Map<String, String> mapFilterFields, String newGroupBy) throws DataException, ElementNotFoundException, FatalException, QueryException {
		this.groupByField = newGroupBy;
		String parentTypeID = HREFLink.getDecodedParameterValue(request, "parenttypeid");
		String parentInstanceID = HREFLink.getDecodedParameterValue(request, "parentinstanceid");
		typeid = HREFLink.getDecodedParameterValue(request, DrilldownParameterNames.TYPE_ID);
		BizSession session = request.getSession();

		Map<String, String> mapAdditionalParameters = getAdditionalParameters(request);
		List<NextInLine> lstGroupInlines = null;
		if (parentTypeID == null) {
			QuerySpec query = getQueryToBeExecuted(session);
			lstGroupInlines = DrilldownProvider.getInstance(session).getGroupInlines(query, mapAdditionalParameters, mapFilterFields, newGroupBy);
		} else {
			lstGroupInlines = DrilldownProvider.getInstance(session).getGroupInlines(parentTypeID, parentInstanceID, typeid, mapAdditionalParameters, mapFilterFields, newGroupBy);
		}
		lstNextInlines = lstGroupInlines;
		return;
	}

	@Override
	public TableCellRenderer getCellRenderer() throws TableModelException {
		return new DrilldownTableCellRenderer(logger, exceptionHandler, messageGenerator);
	}

	@Override
	public TableCellRenderer getColumnCellsRenderer(int colIndex) throws TableModelException {
		return null;
	}

	@Override
	public String getColumnAlignment(int colIndex) throws TableModelException {
		TupleSchemaField schemaField = TupleSchemaFactory.getInstance().getTupleSchema(typeid).getFieldByName(coreFieldNames.get(colIndex));
		DataType dataType = schemaField.getFieldDataType();
		if (BuiltInTypes.STRING.equals(dataType) == true) {
			return super.getColumnAlignment(colIndex);
		}
		if (BuiltInTypes.BOOLEAN.equals(dataType) == true) {
			return super.getColumnAlignment(colIndex);
		}
		if (BuiltInTypes.DATETIME.equals(dataType) == true) {
			return super.getColumnAlignment(colIndex);
		}
		return TableModel.ALIGNMENT_RIGHT;
	}

	@Override
	public String getTimeFilterMetricText() {
		return timeFilterMetricText;
	}

	@Override
	public TableCellRenderer getCellRenderer(int rowIndex, int colIndex) throws TableModelException {
		return null;
	}

	@Override
	public String getGroupByValue() throws TableModelException {
		if (groupByValue == null)
			return null;
		return groupByValue.toString();
	}

	@Override
	public String getGroupByField() throws TableModelException {
		return groupByField;
	}

	@Override
	public int getTotalRowCount() {
		return totalRowCount;
	}

	@Override
	public int[] getDisplayedSpan() throws TableModelException {
		return displaySpan;
	}

	@Override
	public int getDisplayedCount() throws TableModelException {
		if (bPaginationAppended) {
			String dispCount = request.getParameter("dispcount");
			if (dispCount == null) {
				return getRowCount();
			}
			int displayCount = Integer.parseInt(dispCount);
			return displayCount + getRowCount();

		} else {
			return displaySpan[1] - displaySpan[0] + 1;
		}

		/*
		 * String dispCount = request.getParameter("dispcount"); if (dispCount == null) { return getRowCount(); } int displayCount = Integer.parseInt(dispCount); return displayCount + getRowCount();
		 */
		// return displaySpan[1] - displaySpan[0] + 1;
	}

	@Override
	public int getPageSizeCount() {
		return Math.min(DrillDownConfiguration.getTablePageSize(), getRemainingSize());
	}

	@Override
	public int getShowAllThreshold() {
		return DrillDownConfiguration.getTableShowAllThreshold();
	}

	@Override
	public boolean IsShowAll() {
		return (fetchCount <= DrillDownConfiguration.getMaxTableCount());
	}

	public void loadInitialTableData() throws Exception {
		BizSession session = request.getSession();
		// clean up drilldown provider
		DrilldownProvider drilldownProvider = DrilldownProvider.getInstance(session);
		drilldownProvider.clear();
		// type = request.getParameter(DrilldownParameterNames.ETYPE);

		String sortField = HREFLink.getDecodedParameterValue(request, "sortfield");
		boolean sortOrder = getSortedOrder();

		QuerySpec query = getQueryToBeExecuted(session);
		if (query == null) {
			DrilldownRequestStore.setSendEmptyResponse(request, true);
			return;
		}
		typeid = query.getSchema().getTypeID();// request.getParameter(DrilldownParameterNames.ETYPEID);

		fetchCount = drilldownProvider.getInstanceCount(query, mapFilterFields, sortField, sortOrder);
		iteratorId = drilldownProvider.getInstanceData(query, mapFilterFields, sortField, sortOrder);
		dataList = drilldownProvider.getNextSet(iteratorId, getPageSizeCount());
		displaySpan = drilldownProvider.getDisplaySpan(iteratorId);
		if (fetchCount == -1) {
			fetchCount = dataList.size();
		}
		totalRowCount = fetchCount;

		coreFldNamesToCustomFldNamesMap = getDisplayableFields(typeid);

		coreFieldNames = new ArrayList<String>(coreFldNamesToCustomFldNamesMap.keySet());
		headerTitle = getElementName(session, typeid);

		rowCount = dataList.size();
		limitRowCounts(totalRowCount, rowCount);

		columnCount = coreFldNamesToCustomFldNamesMap.size();
		customFieldNames = new ArrayList<String>(coreFldNamesToCustomFldNamesMap.values());

		mapTableParameters = new HashMap<String, Object>();
		mapTableParameters.put(DrilldownParameterNames.TYPE_ID, typeid);
		if (iteratorId != null) {
			mapTableParameters.put(DrilldownParameterNames.ITERATOR_ID, iteratorId);
			mapTableParameters.put(DrilldownParameterNames.FETCH_COUNT, String.valueOf(fetchCount));
		}
		return;
	}

	private QuerySpec getQueryToBeExecuted(BizSession session) throws QueryException {
		// we are in complete new query execution state
		// get the actual query
		List<QuerySpec> querySpecs = BizSessionSearchStore.getInstance(session).getQuerySpecs();
		if (querySpecs == null || querySpecs.isEmpty() == true) {
			return null;
		}
		QuerySpec querySpec = querySpecs.get(0);
		try {
			// create a clone of the the shared query in the
			return (QuerySpec) querySpec.clone();
		} catch (CloneNotSupportedException e) {
			throw new QueryException("could not create a local copy of the shared query specification", e);
		}
	}

	public void loadTableData(Map<String, String> mapFilterFields, int rowSetMode) throws Exception {
		this.mapFilterFields = mapFilterFields;
		typeid = HREFLink.getDecodedParameterValue(request, DrilldownParameterNames.TYPE_ID);
		iteratorId = HREFLink.getDecodedParameterValue(request, "iteratorid");
		String sFetchCount = HREFLink.getDecodedParameterValue(request, "fetchcount");
		if (sFetchCount != null) {
			fetchCount = Integer.parseInt(sFetchCount);
		}

		String parentTypeID = HREFLink.getDecodedParameterValue(request, "parenttypeid");
		String parentInstanceID = HREFLink.getDecodedParameterValue(request, "parentinstanceid");
		String sortField = HREFLink.getDecodedParameterValue(request, "sortfield");
		boolean sortOrder = getSortedOrder();

		if (parentTypeID == null && rowSetMode == TableModel.LOAD_INITIAL_ROW_SET) {
			loadInitialTableData();
			return;
		}

		BizSession session = request.getSession();

		// Get the iterator id to load the data.
		if (sortField != null) {
			// User initiated the Sort
			iteratorId = DrilldownProvider.getInstance(session).getInstanceData(parentTypeID, parentInstanceID, typeid, getAdditionalParameters(request), mapFilterFields, sortField, sortOrder);
		} else if (rowSetMode == TableModel.LOAD_INITIAL_ROW_SET) {
			// User expanding the group row or drill type table.
			iteratorId = DrilldownProvider.getInstance(session).getInstanceData(parentTypeID, parentInstanceID, typeid, getAdditionalParameters(request), mapFilterFields, sortField, sortOrder);
		} else {
			// User is doing pagination and want to load next set of rows.
			// Hence do nothing and use the same iterator id as passed to the API call.
		}

		// Fetch the number of rows to display.
		if (rowSetMode == TableModel.LOAD_ALL_ROW_SET) {
			dataList = DrilldownProvider.getInstance(session).getNextSet(iteratorId, getRemainingSize());
		} else if (rowSetMode == TableModel.LOAD_PREV_ROW_SET) {
			dataList = DrilldownProvider.getInstance(session).getPreviousSet(iteratorId, getPageSizeCount());
		} else if (rowSetMode == TableModel.LOAD_APPEND_ROW_SET) {
			dataList = DrilldownProvider.getInstance(session).getNextSet(iteratorId, getPageSizeCount());
		} else { // if (rowSetMode == TableModel.LOAD_NEXT_ROW_SET)
			dataList = DrilldownProvider.getInstance(session).getNextSet(iteratorId, getPageSizeCount());
		}
		displaySpan = DrilldownProvider.getInstance(session).getDisplaySpan(iteratorId);

		// Process the results and prepare for TableModel methods.
		headerTitle = getElementName(session, typeid);
		coreFldNamesToCustomFldNamesMap = getDisplayableFields(typeid);

		// fetchCount = drilldownHandler.getCountInGroup(session, parentTypeID, parentInstanceID, typeid);
		totalRowCount = fetchCount;

		coreFieldNames = new ArrayList<String>(coreFldNamesToCustomFldNamesMap.keySet());
		rowCount = dataList.size();
		limitRowCounts(totalRowCount, rowCount);

		columnCount = coreFldNamesToCustomFldNamesMap.size();
		customFieldNames = new ArrayList<String>(coreFldNamesToCustomFldNamesMap.values());

		mapTableParameters = new HashMap<String, Object>();
		mapTableParameters.put(DrilldownParameterNames.TYPE_ID, typeid);
		if (parentTypeID != null) {
			mapTableParameters.put("parenttypeid", parentTypeID);
			mapTableParameters.put("parentinstanceid", parentInstanceID);
		}
		if (iteratorId != null) {
			mapTableParameters.put("fetchcount", String.valueOf(fetchCount));
			mapTableParameters.put("iteratorid", iteratorId);
		}
		mapTableParameters.putAll(getAdditionalParameters(request));
	}

	/**
	 * @param session
	 * @param typeid
	 *            TypeId of the element for which name will be returned.
	 * @throws ElementNotFoundException
	 * @throws FatalException
	 */
	private String getElementName(BizSession session, String typeid) {
		return EntityVisualizerProvider.getInstance().getEntityVisualizerById(typeid).getName();
	}

	private Map<String, String> getDisplayableFields(String typeid) {
		return EntityVisualizerProvider.getInstance().getEntityVisualizerById(typeid).getDisplayableFields();
	}

	private boolean getSortedOrder() {
		String sSortState = request.getParameter(TableTreeConstants.KEY_SORT_STATE);
		try {
			int sortState = Integer.parseInt(sSortState);
			if (sortState == 0 || sortState == -1) {
				return false;
			}
			return true;
		} catch (NullPointerException npe) {

		} catch (NumberFormatException nfe) {

		}
		return false;
	}

	private void limitRowCounts(int i_totalRowCount, int i_rowCount) {
		int remainingCount = getRemainingSize();
		totalRowCount = Math.min(DrillDownConfiguration.getMaxTableCount(), i_totalRowCount);
		// totalRowCount = Math.min(remainingCount, totalRowCount);

		rowCount = Math.min(DrillDownConfiguration.getMaxTableCount(), i_rowCount);
		rowCount = Math.min(remainingCount, i_rowCount);
	}

	private int getRemainingSize() {
		Integer loadedRows = (Integer) request.getAttribute("tabletreesize");
		return DrillDownConfiguration.getMaxPageCount() - loadedRows.intValue();
	}

	@Override
	public int getFirstSortOrder() {
		boolean defaultSortOrder = DrillDownConfiguration.isDefaultSortOrderAscending();
		if (defaultSortOrder == true) {
			return TableModel.ASCENDING_ORDER;
		} else {
			return TableModel.DESCENDING_ORDER;
		}
	}

	@Override
	public boolean isGroupByRequired() {
		return true;// groupByRequired;
	}

	@Override
	public boolean isSortRequired() {
		return true;// sortRequired;
	}

	@Override
	public TableMenu getRowMenu(int rowIndex, String menuId) throws TableModelException {
		System.out.println("DrilldownTableModel.getRowMenu()");
		Entity entity = EntityCache.getInstance().getEntity(typeid);
		// are we dealing with a metric ?
		if (entity instanceof Metric) {
			// return no menu
			return null;
		}
		// we are dealing with a concept
		// we will add the menu
		// If menuId need to be rowIndex based then uncomment the following line
		// menuId = menuId + TableTree.PATH_SEPARATOR + rowIndex;
		menuId = "corr_" + menuId;
		TableMenu tableMenu = new TableMenu(menuId, "rowmenu", "Event Fields", true);
		StringBuilder sb = new StringBuilder("genericloader.html?");
		// add uri
		sb.append("uri=");
		sb.append(encode(String.valueOf(request.getAttribute(ConfigurationProperties.PULL_REQUEST_BASE_URL.getName()))));
		// add token
		sb.append("&");
		sb.append(KnownParameterNames.TOKEN);
		sb.append("=");
		sb.append(encode(request.getParameter(KnownParameterNames.TOKEN)));
		// add session id
		sb.append("&");
		sb.append(KnownParameterNames.SESSION_ID);
		sb.append("=");
		sb.append(encode(request.getParameter(KnownParameterNames.SESSION_ID)));
		// add command
		sb.append("&");
		sb.append(KnownParameterNames.COMMAND);
		sb.append("=");
		sb.append("showrelatedconcepts");
		tableMenu.addMenuItem(new MenuProperty("allevent", "Show all related concepts", sb.toString()));
		tableMenu.setShowMenuFunctionName("showDrilldownRowMenu");

		// Next line added to make the JS compatible in absence of variable transfer to query manager base link.
		tableMenu.addShowMenuParameters("");

		// Next line added by to make the JS compatible in absence of remove menu parameter.
		tableMenu.addShowMenuParameters("");
		return tableMenu;
	}

	private String encode(String value) {
		try {
			return URLEncoder.encode(value, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return value;
		}
	}
}
