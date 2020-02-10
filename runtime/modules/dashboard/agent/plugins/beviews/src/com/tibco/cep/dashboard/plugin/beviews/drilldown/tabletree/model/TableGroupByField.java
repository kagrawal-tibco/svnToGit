package com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.model;

import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.integration.TableTreeCreator;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.util.HTMLTag;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.util.JSFunction;


/**
 * @author rajesh
 *
 */
public class TableGroupByField
{

	private static final String GROUP_BY_ID_PREFIX = "GROUP_BY";
	private TableTree table;
	private String fieldName;
	private String fieldDisplayName;
	private String fieldParam;

	private static final String leftImageSrc = "groupbyfieldleft_up.gif";
	private static final String rightImageSrc = "groupbyfieldright_up.gif";

	/**
	 * @param string
	 */
	public TableGroupByField(TableTree table, String fieldName, String fieldDisplayName, String fieldParam)
	{
		this.table = table;
		this.fieldName = fieldName;
		this.fieldDisplayName = fieldDisplayName;
		this.fieldParam = fieldParam;
		JSFunction commandFunction = new JSFunction("addGroupByField");
		commandFunction.setReturn(false);
		commandFunction.addStringParam(table.getTableTreeName());
		commandFunction.addStringParam(table.getTablePath());
		commandFunction.addStringParam(fieldName);

		table.attachCommand(commandFunction.toString());
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return fieldName;
	}

	public void renderCell(HTMLTag tagTR)
	{
		int index = 0;

		/*
			var startIndex = endCell.cellIndex;
			var leftCell = row.insertCell(startIndex);
			leftCell.innerHTML = createLeftImageTagHTML(imagePath, leftImageSrc, 9	);
			leftCell.id = computeId2("GROUP_BY", name, path, groupByFieldText + "0");
		 */

		HTMLTag tagTDLeft = tagTR.addTag("td");
		tagTDLeft.setId(table.computeId(GROUP_BY_ID_PREFIX) + TableTree.ID_SEPARATOR + fieldName + index);

		/*
			var src = imagePath + "/" + imageSrc;
			return "<img src=\"" + src + "\" border=\"0\" width=\"" + width + "\" height=\"21\"/>";
		 */

		HTMLTag tagIMGLeft = tagTDLeft.addTag("img");
		tagIMGLeft.addAttribute("src", table.getImagePath() + "/" + leftImageSrc);
		tagIMGLeft.addAttribute("border", "0");
		tagIMGLeft.addAttribute("width", "9");
		tagIMGLeft.addAttribute("height", "21");

		/*
			startIndex++;
			var titleCell = row.insertCell(startIndex);
			titleCell.className = "tbtr_groupByFieldText";
			titleCell.innerHTML = groupByFieldText;
			titleCell.id = computeId2("GROUP_BY", name, path, groupByFieldText + "1");
			titleCell.c_groupByField = groupByFieldText;
			titleCell.c_groupByParam = groupByFieldParam;
		 */
		index++;
		HTMLTag tagTDTitle = tagTR.addTag("td");
		tagTDTitle.setId(table.computeId(GROUP_BY_ID_PREFIX) + TableTree.ID_SEPARATOR + fieldName + index);
		tagTDTitle.setStyleClass("tbtr_groupByFieldText");
		tagTDTitle.addCustomAttribute("groupByField", fieldName);
		tagTDTitle.addCustomAttribute("groupByParam", fieldParam);
		tagTDTitle.setContent(fieldDisplayName);
		tagTDTitle.addAttribute("nowrap");

		/*
			startIndex++;
			var removeHandler = "return removeGroupBy('" + name + "', '" + path + "', '" + groupByFieldText + "');";
			var rightCell = row.insertCell(startIndex);
			rightCell.innerHTML = createRightImageTagHTML(imagePath, rightImageSrc, 16, removeHandler);
			rightCell.id = computeId2("GROUP_BY", name, path, groupByFieldText + "2");
			rightCell.style.cursor = "hand";
		 */

		index++;
		HTMLTag tagTDRight = tagTR.addTag("td");
		tagTDRight.setId(table.computeId(GROUP_BY_ID_PREFIX) + TableTree.ID_SEPARATOR + fieldName + index);
		tagTDRight.addStyle("cursor", "hand");

		/*
			var src = imagePath + "/" + imageSrc;
			var html = "<img src=\"" + src + "\" border=\"0\" width=\"" + width + "\" height=\"21\"";
			html += " onclick=\"" + onclickHandler + "\" />";
		 */

		HTMLTag tagIMGRight = tagTDRight.addTag("img");
		tagIMGRight.addAttribute("src", table.getImagePath() + "/" + rightImageSrc);
		tagIMGRight.addAttribute("border", "0");
		tagIMGRight.addAttribute("width", "16");
		tagIMGRight.addAttribute("height", "21");
		JSFunction removeHandler = new JSFunction("removeGroupBy");
		removeHandler.setReturn(true);
		removeHandler.addStringParam(table.getTableTreeName());
		removeHandler.addStringParam(table.getTablePath());
		removeHandler.addStringParam(fieldName);
		removeHandler.addStringParam(TableTreeCreator.getParentTableTreePath(table.getTablePath()));
		int parentRowIndex = -1;
		try
		{
			parentRowIndex = Integer.parseInt(table.getHttpServletRequest().getParameter(TableTreeConstants.KEY_ROW_INDEX));
		}
		catch(Exception e)
		{

		}
		removeHandler.addStringParam(parentRowIndex + TableTree.PATH_SEPARATOR + table.getTableIndex());
		tagIMGRight.addAttribute("onclick", removeHandler.toString());

		/*
			startIndex++;
			var spacerCell = row.insertCell(startIndex);
			spacerCell.className = "tbtr_groupByTextTD";
			spacerCell.style.width = "5px";
			spacerCell.innerHTML = "&nbsp;";
			spacerCell.id = computeId2("GROUP_BY", name, path, groupByFieldText + "3");
		 */
		index++;
		HTMLTag tagTDSpacer = tagTR.addTag("td");
		tagTDSpacer.setId(table.computeId(GROUP_BY_ID_PREFIX) + TableTree.ID_SEPARATOR + fieldName + index);
		tagTDSpacer.setStyleClass("tbtr_groupByTextTD");
		tagTDSpacer.addStyle("width", "5px");
		tagTDSpacer.setContent("&nbsp;");
	}

}
