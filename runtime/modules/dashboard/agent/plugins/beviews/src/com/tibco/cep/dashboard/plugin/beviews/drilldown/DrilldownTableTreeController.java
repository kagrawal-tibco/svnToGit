package com.tibco.cep.dashboard.plugin.beviews.drilldown;

import java.util.Map;
import java.util.Properties;

import com.tibco.cep.dashboard.management.ExceptionHandler;
import com.tibco.cep.dashboard.management.MessageGenerator;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.export.DrilldownExportContentHolder;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.model.DrilldownTableModel;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.integration.TableModel;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.integration.TableModelException;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.integration.TableRequest;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.integration.TableTreeController;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.model.TableTree;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.utils.DrillDownConfiguration;
import com.tibco.cep.dashboard.psvr.biz.BizSessionRequest;
import com.tibco.cep.dashboard.psvr.biz.KnownParameterNames;
import com.tibco.cep.dashboard.psvr.common.query.QueryException;
import com.tibco.cep.dashboard.psvr.data.DataException;
import com.tibco.cep.dashboard.security.SecurityToken;
import com.tibco.cep.kernel.service.logging.Logger;

/**
 * @author rajesh
 * 
 */

public class DrilldownTableTreeController extends TableTreeController {

	public static final String BASE_ACTION_LINK = "";

	private static final String TABLE_TREE_WIDGET_ID = "events";

	protected String actionURL;

	public DrilldownTableTreeController(SecurityToken token, Properties properties, Logger logger, ExceptionHandler exceptionHandler, MessageGenerator messageGenerator, BizSessionRequest request) {
		super(token, properties, logger, exceptionHandler, messageGenerator, request);
	}
	
	@Override
	public boolean process() {
		StringBuilder sb = new StringBuilder();
		//add KnownParameterNames.COMMAND
		sb.append(KnownParameterNames.COMMAND);
		sb.append("=");
		sb.append(/*request.getParameter(KnownParameterNames.COMMAND)*/"drilldown");
		//add KnownParameterNames.TOKEN
		sb.append("&");
		sb.append(KnownParameterNames.TOKEN);
		sb.append("=");
		sb.append(request.getParameter(KnownParameterNames.TOKEN));
		//add KnownParameterNames.SESSION_ID
		sb.append("&");
		sb.append(KnownParameterNames.SESSION_ID);
		sb.append("=");
		sb.append(request.getSession().getId());
		//prep for further addition
		sb.append("&");
		actionURL = sb.toString();		
		return super.process();
	}

	public String getActionURL() {
		// return BASE_ACTION_LINK + "?" + KnownParameterNames.SESSION_ID + "="
		// + request.getSession().getId() + "&";
//		return KnownParameterNames.COMMAND + "=" + request.getParameter(KnownParameterNames.COMMAND) + "&" + KnownParameterNames.TOKEN + "=" + request.getParameter(KnownParameterNames.TOKEN) + "&"
//				+ KnownParameterNames.SESSION_ID + "=" + request.getSession().getId() + "&";
		return actionURL;
	}

	public String getTableTreeName() {
		return TABLE_TREE_WIDGET_ID;
	}

	@Override
	protected TableModel getRequestTableModel() {
		return new DrilldownTableModel(token, properties, logger, exceptionHandler, messageGenerator, request);
	}

	public TableModel getStartupTableModel() throws TableModelException {
		DrilldownTableModel tableModel = new DrilldownTableModel(token, properties, logger, exceptionHandler, messageGenerator, request);
		try {
			tableModel.loadInitialTableData();
		} catch (Exception e) {
			throw new TableModelException("Failed to load Startup Table Model", e);
		}
		return tableModel;
	}

	public TableModel[] getNestedTableModels() throws TableModelException {
		DrilldownTableModel parentTableModel = new DrilldownTableModel(token, properties, logger, exceptionHandler, messageGenerator, request);
		try {
			parentTableModel.loadNestedTableData();
			return parentTableModel.getNestedTables();
		} catch (TableModelException tme) {
			throw tme;
		} catch (Exception e) {
			exceptionHandler.handleException("Failed to load Startup Table Model", e);
			throw new TableModelException("Failed to load Startup Table Model", e);
		}
	}

	public TableModel getTableModel(Map<String,String> mapGroupFields, int rowSetMode) throws TableModelException {
		DrilldownTableModel tableModel = new DrilldownTableModel(token, properties, logger, exceptionHandler, messageGenerator, request);
		try {
			tableModel.loadTableData(mapGroupFields, rowSetMode);
		} catch (DataException dre) {
			if (dre.getCause() instanceof QueryException) {
				throw new TableModelException(dre.getCause());
			}
		} catch (Exception e) {
			throw new TableModelException("Failed to load Table Model", e);
		}
		return tableModel;
	}

	protected TableModel[] getGroupedTableModels(Map<String,String> mapGroupFields, String newGroupBy) throws TableModelException {
		DrilldownTableModel parentTableModel = new DrilldownTableModel(token, properties, logger, exceptionHandler, messageGenerator, request);
		try {
			parentTableModel.loadGroupTableData(mapGroupFields, newGroupBy);
		} catch (DataException dre) {
			if (dre.getCause() instanceof QueryException) {
				throw new TableModelException(dre.getCause());
			}
		} catch (Exception e) {
			throw new TableModelException("Failed to load Startup Table Model", e);
		}
		return parentTableModel.getNestedTables();
	}

	public boolean isGroupByEnabled() {
		return true;
	}

	protected boolean isReachedLimit(int currentLoaded) {
		return (!(DrillDownConfiguration.getMaxPageCount() > currentLoaded));
	}

	protected boolean isPaginationInAppendMode() {
		return DrillDownConfiguration.isPaginationInAppendMode();
	}

	protected void removeExportedData(TableRequest tableRequest) {
		tableRequest.getBizRequest().getSession().setAttribute("drilldowncontent", null);
	}

	protected void saveContent(TableModel parentModel, TableModel[] childModels, TableTree tableTree, TableRequest tableRequest) throws TableModelException {
		DrilldownExportContentHolder contentHolder = (DrilldownExportContentHolder) tableRequest.getBizRequest().getSession().getAttribute("drilldowncontent");
		if (contentHolder == null) {
			contentHolder = new DrilldownExportContentHolder(logger,exceptionHandler,messageGenerator);
			tableRequest.getBizRequest().getSession().setAttribute("drilldowncontent", contentHolder);
		}
		contentHolder.saveContent(parentModel, childModels, tableTree, tableRequest);
	}
	
}