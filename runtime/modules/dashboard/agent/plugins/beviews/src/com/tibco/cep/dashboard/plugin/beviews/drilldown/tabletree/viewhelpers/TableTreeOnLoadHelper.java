package com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.viewhelpers;

import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.model.TableTree;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.model.TableTreeConstants;
import com.tibco.cep.dashboard.psvr.biz.BizSessionRequest;

public class TableTreeOnLoadHelper {
	
	public static String getOnLoadCall(String commandType, TableTree tableTree, BizSessionRequest request){
		
		String bodyOnload = "";
	
		if (commandType == null || commandType.length() == 0)
		{
			commandType = TableTreeConstants.CMD_FULL_TABLE;
		}
	
		if (TableTreeConstants.CMD_FULL_TABLE.equalsIgnoreCase(commandType))
		{
//			bodyOnload = " onload = \"return transferBaseTreeTable();\"";
			bodyOnload = " onload = \"return runCommands();\"";
		}
		else
		if (TableTreeConstants.CMD_ROW_EXPAND.equalsIgnoreCase(commandType))
		{
			bodyOnload = " onload = \"return transferTree("
							+ "'" + tableTree.getTableTreeName()
							+ "', '" +  tableTree.getTablePath()
							+ "', '" +  request.getParameter(TableTreeConstants.KEY_ROW_INDEX)
							+ "');\"";
		}
		else
		if (TableTreeConstants.CMD_TABLE_EXPAND.equalsIgnoreCase(commandType))
		{
			bodyOnload = " onload = \"return transferNestedTable("
							+ "'" + tableTree.getTableTreeName()
							+ "', '" +  tableTree.getTablePath()
							+ "', '" +  request.getParameter(TableTreeConstants.KEY_PARENT_TABLE_PATH)
							+ "', '" +  request.getParameter(TableTreeConstants.KEY_ROW_PATH)
							+ "');\"";
		}
		else
		if (TableTreeConstants.CMD_REFRESH.equalsIgnoreCase(commandType))
		{
			bodyOnload = " onload = \"return transferTable("
							+ "'" + tableTree.getTableTreeName()
							+ "', '" +  tableTree.getTablePath()
							+ "');\"";
		}
		else
		if (TableTreeConstants.CMD_SORT.equalsIgnoreCase(commandType))
		{
			bodyOnload = " onload = \"return transferNestedTable("
							+ "'" + tableTree.getTableTreeName()
							+ "', '" +  tableTree.getTablePath()
							+ "', '" +  request.getParameter(TableTreeConstants.KEY_PARENT_TABLE_PATH)
							+ "', '" +  request.getParameter(TableTreeConstants.KEY_ROW_PATH)
							+ "');\"";
		}
		else
		if (TableTreeConstants.CMD_GROUP_BY.equalsIgnoreCase(commandType))
		{
			bodyOnload = " onload = \"return transferGroupRows("
							+ "'" + tableTree.getTableTreeName()
							+ "', '" +  tableTree.getTablePath()
							+ "', '" +  request.getParameter(TableTreeConstants.KEY_PARENT_TABLE_PATH)
							+ "', '" +  request.getParameter(TableTreeConstants.KEY_ROW_PATH)
							+ "');\"";
		}
		else
		if (TableTreeConstants.CMD_TABLE_NEXT_ROW_SET.equalsIgnoreCase(commandType))
		{
			bodyOnload = " onload = \"return transferNextRowSet("
							+ "'" + tableTree.getTableTreeName()
							+ "', '" +  tableTree.getTablePath()
							+ "');\"";
		}
		else
		if (TableTreeConstants.CMD_TABLE_PREV_ROW_SET.equalsIgnoreCase(commandType))
		{
			bodyOnload = " onload = \"return transferPrevRowSet("
							+ "'" + tableTree.getTableTreeName()
							+ "', '" +  tableTree.getTablePath()
							+ "');\"";
		}
		else
		if (TableTreeConstants.CMD_TABLE_APPEND_ROW_SET.equalsIgnoreCase(commandType))
		{
			bodyOnload = " onload = \"return transferAppendRowSet("
							+ "'" + tableTree.getTableTreeName()
							+ "', '" +  tableTree.getTablePath()
							+ "');\"";
		}
		else
		if (TableTreeConstants.CMD_EXPAND_ALL.equalsIgnoreCase(commandType))
		{
			bodyOnload = " onload = \"return transferNestedTableExpanded("
							+ "'" + tableTree.getTableTreeName()
							+ "', '" +  tableTree.getTablePath()
							+ "', '" +  request.getParameter(TableTreeConstants.KEY_PARENT_TABLE_PATH)
							+ "', '" +  request.getParameter(TableTreeConstants.KEY_ROW_PATH)
							+ "');\"";
		}
		return bodyOnload;
	}
}