package com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.integration;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.management.ExceptionHandler;
import com.tibco.cep.dashboard.management.MessageGenerator;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.model.NestedTableTree;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.model.TableTree;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.model.TableTreeConstants;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.util.JSFunction;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.viewhelpers.TableTreeMenuHelper;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.viewhelpers.TableTreeOnLoadHelper;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.utils.DrilldownRequestStore;
import com.tibco.cep.dashboard.psvr.biz.BizSession;
import com.tibco.cep.dashboard.psvr.biz.BizSessionRequest;
import com.tibco.cep.dashboard.security.SecurityToken;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

//URL for testing http://localhost:6161/executive/mi2/loadpage.do?pid=2&typeid=023@6148@023@6148&fld_State=Create

/**
 * @author rajesh
 */
public abstract class TableTreeController implements TableTreeConstants {

	protected Logger logger;

	protected ExceptionHandler exceptionHandler;

	protected MessageGenerator messageGenerator;

	protected BizSessionRequest request;
	
	protected Properties properties;
	
	protected SecurityToken token;

	private TableRequest tableRequest;

	/**
	 * 
	 */
	public TableTreeController(SecurityToken token, Properties properties, Logger logger, ExceptionHandler exceptionHandler, MessageGenerator messageGenerator, BizSessionRequest request) {
		super();
		this.token = token;
		this.properties = properties;
		this.request = request;
		this.logger = new SessionPrefixingLogger(request.getSession(), logger);
		this.exceptionHandler = exceptionHandler;
		this.messageGenerator = messageGenerator;
		tableRequest = new TableRequest(request);
	}

	@SuppressWarnings("unchecked")
	public boolean process() {
		long stime = System.currentTimeMillis();
		long sstime = System.currentTimeMillis();
		String commandType = request.getParameter(KEY_CMD);
		updateSizeMapOnRequest(commandType);
		TableTreeSizeController tableTreeSizeController = getTableTreeSizeController();
		if (tableTreeSizeController.isCommandToIncreaseTableTreeSize(commandType) && isReachedLimit(tableTreeSizeController.getTableTreeSize())) {
			processError("You have reached the limit to download the records");
			return false;
		}
		if (logger.isEnabledFor(Level.DEBUG) == true) {
			logger.log(Level.DEBUG, "Starting processing of TableRequest[tableTreePath=" + tableRequest.getTableTreePath() + "]...");
		}
		GroupByRequest groupByRequest = new GroupByRequest(request);
		Map<String, String> mapGroupFields = groupByRequest.getModelGroupByFieldMap();
		String modelGroupByField = groupByRequest.getGroupByField();
		saveTableTreeSizeToRequest();
		boolean bSendEmptyResponse = false;
		List<String> commands = new LinkedList<String>();
		try {
			TableTree tableTree = null;
			TableModel parentTableModel = null;
			TableModel[] childTableModels = null;
			// String path = request.getParameter(KEY_PATH);

			if (commandType == null || commandType.length() == 0) {
				commandType = CMD_FULL_TABLE;
			}
			if (logger.isEnabledFor(Level.DEBUG) == true) {
				logger.log(Level.DEBUG, "Processing TableRequest[tableTreePath=" + tableRequest.getTableTreePath() + "] using " + commandType + " as the command...");
			}
			if (CMD_FULL_TABLE.equalsIgnoreCase(commandType)) {
				removeExportedData(tableRequest);
				TableTreeCreator tableTreeCreator = new TableTreeCreator(tableRequest);
				parentTableModel = getStartupTableModel();
				bSendEmptyResponse = DrilldownRequestStore.isSendEmptyResponse(request);
				if (bSendEmptyResponse == false) {
					tableTree = tableTreeCreator.createInitialTableTree(getTableTreeName(), parentTableModel, this);
				}
				else {
					commands.add("disableActions();");
				}
			} else if (CMD_ROW_EXPAND.equalsIgnoreCase(commandType)) {
				TableTreeCreator tableTreeCreator = new TableTreeCreator(tableRequest);
				parentTableModel = getRequestTableModel();
				childTableModels = getNestedTableModels();
				tableTree = tableTreeCreator.createNestedTables(getTableTreeName(), parentTableModel, childTableModels, this, false);
			} else if (CMD_TABLE_EXPAND.equalsIgnoreCase(commandType)) {
				TableTreeCreator tableTreeCreator = new TableTreeCreator(tableRequest);
				parentTableModel = getTableModel(mapGroupFields, TableModel.LOAD_INITIAL_ROW_SET);
				tableTree = tableTreeCreator.createExpandedTable(getTableTreeName(), parentTableModel, this, groupByRequest);
			} else if (CMD_REFRESH.equalsIgnoreCase(commandType)) {
				TableTreeCreator tableTreeCreator = new TableTreeCreator(tableRequest);
				parentTableModel = getTableModel(mapGroupFields, TableModel.LOAD_INITIAL_ROW_SET);
				if (tableRequest.isLoaded()) {
					tableTree = tableTreeCreator.createFullTable(getTableTreeName(), parentTableModel, this, new GroupByRequest(), tableRequest.isExpanded());
				} else {
					tableTree = tableTreeCreator.createHeaderTable(getTableTreeName(), parentTableModel, this);
				}
			} else if (CMD_GROUP_BY.equalsIgnoreCase(commandType)) {
				TableTreeCreator tableTreeCreator = new TableTreeCreator(tableRequest);
				parentTableModel = getRequestTableModel();// getTableModel(mapGroupFields, TableModel.LOAD_INITIAL_ROW_SET);
				childTableModels = getGroupedTableModels(mapGroupFields, modelGroupByField);
				tableTree = tableTreeCreator.createGroupByTable(getTableTreeName(), parentTableModel, childTableModels, this);
			} else if (CMD_TABLE_APPEND_ROW_SET.equalsIgnoreCase(commandType)) {
				TableTreeCreator tableTreeCreator = new TableTreeCreator(tableRequest);
				parentTableModel = getTableModel(mapGroupFields, TableModel.LOAD_APPEND_ROW_SET);
				int currentTableSize = 0;// tableTreeSizeController.getTableSize(tableRequest.getTableTreePath());
				tableTree = tableTreeCreator.createRowSetTable(getTableTreeName(), parentTableModel, this, groupByRequest, currentTableSize);
			} else if (CMD_TABLE_NEXT_ROW_SET.equalsIgnoreCase(commandType)) {
				TableTreeCreator tableTreeCreator = new TableTreeCreator(tableRequest);
				parentTableModel = getTableModel(mapGroupFields, TableModel.LOAD_NEXT_ROW_SET);
				tableTree = tableTreeCreator.createRowSetTable(getTableTreeName(), parentTableModel, this, groupByRequest, 0);
			} else if (CMD_TABLE_PREV_ROW_SET.equalsIgnoreCase(commandType)) {
				TableTreeCreator tableTreeCreator = new TableTreeCreator(tableRequest);
				parentTableModel = getTableModel(mapGroupFields, TableModel.LOAD_PREV_ROW_SET);
				tableTree = tableTreeCreator.createRowSetTable(getTableTreeName(), parentTableModel, this, groupByRequest, 0);
			} else if (CMD_SORT.equalsIgnoreCase(commandType)) {
				TableTreeCreator tableTreeCreator = new TableTreeCreator(tableRequest);
				parentTableModel = getTableModel(mapGroupFields, TableModel.LOAD_INITIAL_ROW_SET);
				if (tableRequest.isLoaded()) {
					tableTree = tableTreeCreator.createFullTable(getTableTreeName(), parentTableModel, this, groupByRequest, tableRequest.isExpanded());
				} else {
					tableTree = tableTreeCreator.createHeaderTable(getTableTreeName(), parentTableModel, this);
				}
			} else if (CMD_EXPAND_COLLAPSE.equalsIgnoreCase(commandType)) {
				bSendEmptyResponse = true;
			} else if (CMD_EXPAND_ALL.equalsIgnoreCase(commandType)) {
				TableTreeCreator tableTreeCreator = new TableTreeCreator(tableRequest);
				tableTree = tableTreeCreator.createNestedTablesWithColumnRows(getTableTreeName(), getRequestTableModel(), getNestedTableModels(), this, false);
				tableTree.setWidthType(TableTree.WIDTH_FULL);
				NestedTableTree[] nestedTables = tableTree.getNestedTables();
				for (int i = 0; i < nestedTables.length; i++) {
					NestedTableTree nestedTableTree = nestedTables[i];
					nestedTableTree.setParentRowCount(nestedTables.length);
					nestedTableTree.setParentRowIndex(i);
				}
			}

			long etime = System.currentTimeMillis();
			if (logger.isEnabledFor(Level.DEBUG) == true) {
				logger.log(Level.DEBUG, "Processing TableRequest[tableTreePath=" + tableRequest.getTableTreePath() + "] took " + (etime - stime) + " msecs...");
			}
			if (bSendEmptyResponse) {
				if (CMD_EXPAND_COLLAPSE.equalsIgnoreCase(commandType) == true) {
					request.setAttribute("sendemptyresponse", "EXPANDCOLLAPSE");
				}
			}
			else {
				stime = System.currentTimeMillis();
				saveContent(parentTableModel, childTableModels, tableTree, tableRequest);
				etime = System.currentTimeMillis();
				if (logger.isEnabledFor(Level.DEBUG) == true) {
					logger.log(Level.DEBUG, "Saving content Of TableRequest[tableTreePath=" + tableRequest.getTableTreePath() + "] took " + (etime - stime) + " msecs...");
				}
				request.setAttribute(TableTreeConstants.TreeTable_TABLE, tableTree);
				request.setAttribute("tableTreeHtml", tableTree.getHTML());
//				request.setAttribute("bodyOnLoad", TableTreeOnLoadHelper.getOnLoadCall(commandType, tableTree, request));
				request.setAttribute("popupMenuHtml", TableTreeMenuHelper.getMenuHtml(request));
				
				if (request.getAttribute(TableTree_COMMANDS) != null) {
					commands.addAll((List<String>)request.getAttribute(TableTree_COMMANDS));
				}
				if (request.getAttribute("runCommands") != null) {
					commands.addAll((List<String>)request.getAttribute("runCommands"));					
				}

				request.setAttribute(KEY_CMD, commandType);
				stime = System.currentTimeMillis();
				updateTableTreeSizeOnResponse(commandType, tableTree, parentTableModel);
				etime = System.currentTimeMillis();
				if (logger.isEnabledFor(Level.DEBUG) == true) {
					logger.log(Level.DEBUG, "Updating of Size On TableRequest[tableTreePath=" + tableRequest.getTableTreePath() + "] took " + (etime - stime) + " msecs...");
				}				
			}
			
			request.setAttribute("runCommands", commands);
			request.setAttribute("bodyOnLoad", TableTreeOnLoadHelper.getOnLoadCall(commandType, tableTree, request));
			
			return true;
		} catch (TableModelException e) {
			exceptionHandler.handleException(e);
//			if (commandType != CMD_FULL_TABLE) {
				processError("could not retrieve data due to [" + e.getCause().getMessage() + "]");
//			}
			return false;
		} finally {
			if (logger.isEnabledFor(Level.DEBUG) == true) {
				long etime = System.currentTimeMillis();
				logger.log(Level.DEBUG, "Completed processing of TableRequest[tableTreePath=" + tableRequest.getTableTreePath() + "] in " + (etime - sstime) + " msecs...");
			}
		}
	}
	
	public void processError(String message){
		String commandType = request.getParameter(KEY_CMD);
		if (StringUtil.isEmptyOrBlank(commandType) == true){
			commandType = CMD_FULL_TABLE;
		}
		request.setAttribute("errormessage", message);
		if (commandType != CMD_FULL_TABLE) {
			JSFunction jsFunction = new JSFunction("showmsgandresetcursor");
			jsFunction.addStringParam(message);
			jsFunction.addStringParam(getTableTreeName());
			jsFunction.addStringParam(tableRequest.getTableTreePath());
			jsFunction.addStringParam(request.getParameter(TableTreeConstants.KEY_ROW_INDEX));
			request.setAttribute("bodyOnLoad", "onload=\""+jsFunction+"\"");
		}
	}

	protected abstract boolean isReachedLimit(int currentLoaded);

	protected TableModel getRequestTableModel() {
		return new RequestTableModel(request);
	}

	protected abstract String getActionURL();

	protected abstract String getTableTreeName();

	protected abstract TableModel getStartupTableModel() throws TableModelException;

	protected abstract TableModel[] getNestedTableModels() throws TableModelException;

	protected abstract TableModel getTableModel(Map<String, String> mapGroupFields, int rowSetMode) throws TableModelException;

	protected abstract TableModel[] getGroupedTableModels(Map<String, String> mapGroupFields, String newGroupBy) throws TableModelException;

	protected abstract boolean isPaginationInAppendMode();

	public abstract boolean isGroupByEnabled();

	protected abstract void removeExportedData(TableRequest tableRequest);

	protected abstract void saveContent(TableModel parentModel, TableModel[] childModel, TableTree tableTree, TableRequest tableRequest) throws TableModelException;

	protected void updateTableTreeSizeOnResponse(String commandType, TableTree tableTree, TableModel tableModel) throws TableModelException {
		if (tableModel == null || tableTree == null) {
			return;
		}
		TableTreeSizeController sizeController = getTableTreeSizeController();
		sizeController.updateOnResponse(commandType, tableTree, tableModel);
	}

	/**
	 * @return
	 */
	private TableTreeSizeController getTableTreeSizeController() {
		TableTreeSizeController sizeController = (TableTreeSizeController) request.getSession().getAttribute("map_size_" + getTableTreeName());
		if (sizeController == null) {
			sizeController = new TableTreeSizeController(logger, exceptionHandler, messageGenerator);
			request.getSession().setAttribute("map_size_" + getTableTreeName(), sizeController);
		}
		return sizeController;
	}

	/**
	 * @param commandType
	 * @param mapSize
	 */
	private void saveTableTreeSizeToRequest() {
		TableTreeSizeController sizeController = getTableTreeSizeController();
		int totalSize = sizeController.getTableTreeSize();
		request.setAttribute("tabletreesize", new Integer(totalSize));
	}

	/**
	 * @param commandType
	 * @param mapSize
	 */
	private void updateSizeMapOnRequest(String commandType) {
		String requestPath = request.getParameter(KEY_PATH);
		TableTreeSizeController sizeController = getTableTreeSizeController();
		sizeController.updateOnRequest(commandType, requestPath);
	}

	class SessionPrefixingLogger implements Logger {

		private Logger logger;

		private String logPrefix;

		SessionPrefixingLogger(BizSession session, Logger logger) {
			if (session != null){
				logPrefix = session.getId();
			}
			else {
				logPrefix = "Unknown/Invalidated Session";
			}
			this.logger = logger;
		}

		public void close() {
			logger.close();
		}

		public Level getLevel() {
			return logger.getLevel();
		}

		public String getName() {
			return logger.getName();
		}

		public boolean isEnabledFor(Level level) {
			return logger.isEnabledFor(level);
		}

		public void log(Level level, String format, Object... args) {
			logger.log(level, logPrefix + "::" + format, args);
		}

		public void log(Level level, String format, Throwable thrown, Object... args) {
			logger.log(level, logPrefix + "::" + format, thrown, args);
		}

		public void log(Level level, String msg) {
			logger.log(level, logPrefix + "::" + msg);
		}

		public void log(Level level, Throwable thrown, String format, Object... args) {
			logger.log(level, thrown, logPrefix + "::" + format, args);
		}

		public void log(Level level, Throwable thrown, String msg) {
			logger.log(level, thrown, logPrefix + "::" + msg);
		}

		public void setLevel(Level level) {
			logger.setLevel(level);
		}

	}
}
