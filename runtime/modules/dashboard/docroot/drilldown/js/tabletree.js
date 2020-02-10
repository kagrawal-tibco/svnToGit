var STATE_LOADED = "LOADED";
var STATE_LOADING = "LOADING";
var currentState = STATE_LOADED;

var NODE_ROW_PREFIX = "tableTree_Row";
var TABLE_CONTENT_ID = "tblTreeMain";
var FRAME_CONTENT_ID = "ifrmMain";

//For Nested Table and Group Row
//var NESTED_CELL_ID = "tableTree_GroupRow_NESTED_TABLE";
var GROUPROW_TABLE_ID_PREFIX = "tableTree_GroupRow_TABLE";  //tableTree_GroupRow_GROUPROW_TABLE
var GROUPROW_HEADER_ID_PREFIX = "tableTree_GroupRow_HEADER";//tableTree_GroupRow_GROUPROW_HEADER

var EX_COL_IMG_PREFIX = "tableTree_ExCol_Image";
var EX_COL_LINK_PREFIX = "tableTree_ExCol_Link";
var ROW_HEADER_IMAGE = "tableTree_RowHeader_Image";
var FILTER_INPUT_ID_PREFIX = "tableTree_FilterRow_INPUT";
var NODE_CELL_PREFIX = "tableTree_Cell";

var COL_HDR_IMAGE = "tableTree_COLUMN_HEADER_IMAGE";
var COL_HDR_TH_ID_PREFIX = "tableTree_COLUMN_HEADER_TH";
var COL_HDR_ID_PREFIX = "tableTree_COLUMN_HEADER";
var COL_ID_PREFIX = "tableTree_COLUMN";

var COL_HEADER_ROW_ID_PREFIX = "colHeaderRow";

var TYPE_ROW = "row";
var TYPE_GROUP_ROW = "group_row";
var TYPE_NESTED_TABLE_ROW = "nestedtable_row";

var NESTED_TD_ID_PREFIX = "tableTree_NestedTable_CELL";
var NESTED_ROW_ID_PREFIX = "tableTree_NestedTable_ROW";


var DELIMITER = "*";
var ID_SEPARATOR = "*";
var PATH_SEPARATOR = "_";

var COLUMN_OFFSET = 2;

var WIDTH_FULL = 0;
var WIDTH_NATURAL = 1;


var styleTreeLine = "cellTreeLine";
var styleNoTreeLine = "cellNoTreeLine";


//var removeOnCollapse = false;

/*
	Called when user clicks on the expand/collapse handle.
*/
//function expandCollapseTree(path, image1, image2, image3, name)
function expandCollapseTree(name, tablePath, rowPath, rowPrefix, rowIndex, nestedTablePath)
{
	//alert(removeOnCollapse);
	if (currentState == STATE_LOADING)
	{
		window.status = "System is loading previous node children. Please wait and try again...";
		return false;
	}

	//Swap the expand/collapse image.
	var row = document.getElementById(computeId2(rowPrefix, name, tablePath, rowPath));

	var image = document.getElementById(computeId2(EX_COL_IMG_PREFIX, name, tablePath, rowPath));

	if (row.c_expanded == "true")
	{
		image.src = image.c_CloseImage;
	}
	else
	{
		image.src = image.c_OpenImage;
	}

	if (removeOnCollapse)
	{
		return handleRemoveOnCollapse(row, image, name, tablePath, nestedTablePath, rowPath, rowPrefix);
	}
	else
	{
		return handleHideOnCollapse(row, image, name, tablePath, nestedTablePath, rowPath, rowPrefix);
	}
}

function handleHideOnCollapse(row, image, name, tablePath, nestedTablePath, rowPath, rowPrefix)
{
	//Check if the tree is loaded.
	if (row.c_loaded == "false")
	{
		//Tree is not loaded. Hence return true to allow HREF to work to load tree content
		//from server in the hidden iframe.
		row.c_loaded = "true";
		row.c_expanded = "true";
		image.src = image.c_ProgressImage;
		image.c_type = "progress";
		loadStart();
		return true;
	}
	else
	{
		//Tree is already loaded. Change the expand/collapse state.
		expandCollapseNode(row, name, nestedTablePath, rowPath, rowPrefix);
		var linkNode = document.getElementById(computeId2(EX_COL_LINK_PREFIX, name, tablePath, rowPath));
		linkNode.href = "javascript://";
		sendExpandCollapseRequest(name, tablePath, nestedTablePath, rowPath, row);
		return false;
	}
}

function handleRemoveOnCollapse(row, image, name, tablePath, nestedTablePath, rowPath, rowPrefix)
{

	//Check if the tree is loaded.
	if (row.c_expanded == "false")
	{
		//Tree is not expanded. Hence return true to allow HREF to work to load tree content
		//from server in the hidden iframe.
		row.c_loaded = "true";
		row.c_expanded = "true";
		image.src = image.c_ProgressImage;
		image.c_type = "progress";
		loadStart();
		return true;
	}
	else
	{
		removeSubTree(row, name, nestedTablePath);
		row.c_expanded = "false";
		sendExpandCollapseRequest(name, tablePath, nestedTablePath, rowPath, row);
		return false;
	}
}


function expandCollapseNode(rowParent, name, path, rowPath, rowPrefix)
{
	if (rowParent.c_expanded == "true")
	{
		//Row is expanded. Collapse it.
		rowParent.c_expanded = "false";
		showDescendents(rowParent, name, path, false);
	}
	else
	{
		//Row is collapsed. Expand it.
		rowParent.c_expanded = "true";
		showDescendents(rowParent, name, path, true);
	}
	return false;
}

function sendExpandCollapseRequest(name, tablePath, nestedTablePath, rowPath, row)
{
	if (!bExpandCollapseRequest) return;
	var KEY_CMD = "cmd";
	var CMD_EXPAND_COLLAPSE = "expandcollapse";

	var path = "";
	if (nestedTablePath == undefined)
	{
		path = tablePath + PATH_SEPARATOR + rowPath;
	}
	else
	{
		path = nestedTablePath;
	}

	var expandCollapseLink = baseAction + KEY_CMD + "=" + CMD_EXPAND_COLLAPSE + "&expanded=" + row.c_expanded + "&tableTreePath=" + path + "&tableTreeId=" + name;
	var frameId = computeId3(FRAME_CONTENT_ID, name);
	document.frames[frameId].location.href = expandCollapseLink;
}

function showDescendents(rowParent, name, path, bShow)
{
	if (bShow)
	{
		showSubTree(rowParent, name, path);
	}
	else
	{
		hideSubTree(rowParent, name, path);
	}
}

function showSubTree(row, name, path)
{
	if (row.c_type == TYPE_ROW)
	{
		showChildren(row);
	}
	else if (row.c_type == TYPE_NESTED_TABLE_ROW)
	{
		showNestedTable(document, name,path);
	}
}

function hideSubTree(row, name, path)
{
	if (row.c_type == TYPE_ROW)
	{
		hideChildren(row);
	}
	else if (row.c_type == TYPE_NESTED_TABLE_ROW)
	{
		hideNestedTable(document, name, path);
	}
}

function removeSubTree(row, name, path)
{
	if (row.c_type == TYPE_ROW)
	{
		removeChildren(row);
	}
	else if (row.c_type == TYPE_NESTED_TABLE_ROW)
	{
		removeNestedTable(document, name, path);
	}
}

function showChildren(row)
{
	var arr_childRows = row.c_childRows;
	var rowIndex = 0;
	for (;rowIndex < arr_childRows.length; rowIndex++)
	{
		var childRow = arr_childRows[rowIndex];
		childRow.style.display = "";
		if (childRow.c_loaded=="true" && childRow.c_type=="row" && childRow.c_expanded == "true")
		{
			showChildren(childRow);
		}
	}
}

function hideChildren(row, displayStyle)
{
	var arr_childRows = row.c_childRows;
	var rowIndex = 0;
	for (;rowIndex < arr_childRows.length; rowIndex++)
	{
		var childRow = arr_childRows[rowIndex];
		childRow.style.display = "none";
		if (childRow.c_loaded=="true" && childRow.c_type=="row" && childRow.c_expanded == "false")
		{
			hideChildren(childRow);
		}
	}
}

function showNestedTable(inDocument, name, path)
{
	var table = getTreeTable(name,path);
	table.tBodies[0].style.display = "";

	var colHeaderRowId = computeId(COL_HEADER_ROW_ID_PREFIX, name, path);
	var colHeaderRow = document.getElementById(colHeaderRowId);
	if (colHeaderRow)
	{
		colHeaderRow.style.display = "";
	}
}

function hideNestedTable(inDocument, name, path)
{
	var table = getTreeTable(name,path);
	table.tBodies[0].style.display = "none";

	var colHeaderRowId = computeId(COL_HEADER_ROW_ID_PREFIX, name, path);
	var colHeaderRow = document.getElementById(colHeaderRowId);
	if (colHeaderRow)
	{
		colHeaderRow.style.display = "none";
	}
}

function removeChildren(row, displayStyle)
{
	var arr_childRows = row.c_childRows;
	var rowIndex = 0;
	for (;rowIndex < arr_childRows.length; rowIndex++)
	{
		var childRow = arr_childRows[rowIndex];
		var table = childRow.parentNode; //It will be the tbody collection
		table.deleteRow(childRow.sectionRowIndex);
	}
	row.c_childRows = new Array();
}

function removeNestedTable(inDocument, name, path)
{
	var table = getTreeTable(name,path);
	tbtr_removeRows(table.tBodies[0]);

	var colHeaderRowId = computeId(COL_HEADER_ROW_ID_PREFIX, name, path);
	var colHeaderRow = document.getElementById(colHeaderRowId);
	if (colHeaderRow)
	{
		table.deleteRow(colHeaderRow.rowIndex);
	}
}

function getRowId(name, path, rowIndex)
{
	return computeId2(NODE_ROW_PREFIX, name, path, rowIndex);
}


function getColumnHeaderTD (name, path , colIndex)
{
	return document.getElementById(computeId2(COL_HDR_ID_PREFIX, name, path, colIndex));
}

function getRow(name, path , rowIndex)
{
	return document.getElementById(getRowId(name, path, rowIndex));

}

function getRowHeaderImage(name, path , rowIndex)
{
	return document.getElementById(computeId2(ROW_HEADER_IMAGE, name, path, rowIndex));

}
function getTreeTable(name,path)
{
	return 	document.getElementById(computeId(TABLE_CONTENT_ID, name, path));
}

function getTableWidthType(name, path)
{

	return parseInt(getTreeTable(name, path).c_widthType);
}

function isTableExpandCollapseCell(name, path)
{
	return getTreeTable(name, path).c_expandCollapseCell == "true";
}

function getCells(name, path, row)
{
	var tableWidthType = getTableWidthType(name, path);
	var cells = row.cells;
	var firstCellIndex = COLUMN_OFFSET;

	if (!isTableExpandCollapseCell(name, path))
	{
		firstCellIndex--;
	}

	var lastCellIndex = cells.length;
	if (tableWidthType == WIDTH_NATURAL)
	{
		lastCellIndex--;
	}

	var r_cells = new Array(lastCellIndex - firstCellIndex);
	for (var i=firstCellIndex; i<lastCellIndex; i++)
	{
		r_cells[i-firstCellIndex] = cells[i];
	}
	return r_cells;
}

function getCell(row, colIndex)
{
	if (isTableExpandCollapseCell(name, path))
	{
		return row.cells[COLUMN_OFFSET + colIndex];
	}
	else
	{
		return row.cells[COLUMN_OFFSET + colIndex - 1];
	}
}

function computeId(prefix, name, tablePath)
{
	return prefix + ID_SEPARATOR + name + PATH_SEPARATOR + tablePath;
}

function computeId2(prefix, name, tablePath, rowPath)
{
	return computeId(prefix, name, tablePath) + ID_SEPARATOR + rowPath;
}

function computeId3(prefix, name)
{
	return prefix + ID_SEPARATOR + name;
}

function loadStart()
{
	currentState = STATE_LOADING;
	window.status = "System is loading data. Please wait...";
	setWaitCursor(document.body);
}

function loadOver(parentDocument)
{
	//Update the window status.
	window.parent.currentState = STATE_LOADED;
	window.parent.status = "";
	document.body.innerHTML = "";
	document.clear();
	resetWaitCursor(parentDocument.body);
}

function setWaitCursor(element)
{
	element.style.cursor = "wait";
}

function resetWaitCursor(element)
{
	element.style.cursor = "auto";
}


/*********************

START: Transfer from hidden frame to Main Frame related methods.

*************************/

function transferNestedTable(name, path, parentPath, rowPath)
{
	transferMenus();
	var tblTreeTableSource = document.getElementById(computeId(TABLE_CONTENT_ID, name, path));

	var parentDocument = window.parent.document;

	if (tblTreeTableSource == null || tblTreeTableSource.rows.length == 0)
	{
		noChildrenFound(parentDocument, name, parentPath, rowPath);
	}
	else
	{
		//Get the Target table to transfer the source table contents.
		var tblTreeTableTarget = parentDocument.getElementById(computeId(TABLE_CONTENT_ID, name, path));
		//Remove the old Column Header.
		tbtr_removeColumnHeader(name, path, parentDocument, tblTreeTableTarget);
		//Remove the Old Rows.
		tbtr_removeRows(tblTreeTableTarget.tBodies[0]);

		//Add the new Column Header, Table Header will not be there in the source table.
		tbtr_transferRows(tblTreeTableSource.tHead, tblTreeTableTarget.tHead);

		//Hide the column header if the table is in collapsed state. Happens when the last group by is removed and that group table is collapsed.
		tbtr_hideColumnHeader(parentDocument, name, path, parentPath, rowPath);

		//Add the new rows.
		tbtr_transferRows(tblTreeTableSource.tBodies[0], tblTreeTableTarget.tBodies[0]);

		//Remove the Progress Bar set on the row expand state
		removeProgressBar(parentDocument, name, parentPath, rowPath);
	}

	loadOver(parentDocument);

}

function removeProgressBar(parentDocument, name, parentPath, rowPath)
{
	//Remove the Progress Bar set on the row expand state
	var image = parentDocument.getElementById(computeId2(EX_COL_IMG_PREFIX, name, parentPath, rowPath));
	if (image == null || image == undefined)
	{
		return;
	}
	if (image.c_type == "progress")
	{
		image.src = image.c_OpenImage;
		image.c_type = "";
	}
}

function transferNestedTableExpanded(name, tablePath)
{
	transferMenus();
	var tblTreeTableSource = document.getElementById(computeId(TABLE_CONTENT_ID, name, tablePath));
	var parentDocument = window.parent.document;
	var tblTreeTableTarget = parentDocument.getElementById(computeId(TABLE_CONTENT_ID, name, tablePath));

	var numRows = tblTreeTableSource.tBodies[0].rows.length;
	if (numRows == 0)
	{
		loadOver(window.parent.document);
		return;
	}
	for (var rowIndex=0;rowIndex<numRows; rowIndex++)
	{
		var anchorRow = parentDocument.getElementById(computeId2(NODE_ROW_PREFIX, name, tablePath, rowIndex));
		if (anchorRow.c_loaded == "true")
		{
			if (anchorRow.c_expanded == "false")
			{
				var image = parentDocument.getElementById(computeId2(EX_COL_IMG_PREFIX, name, tablePath, rowIndex));
				image.src = image.c_OpenImage;
				anchorRow.c_expanded = "true";
				showDescendents(anchorRow, name, tablePath, true);
			}
			continue;
		}
		anchorRow.c_childRows = new Array(1);
		var anchorIndex = anchorRow.rowIndex;
		var rowSource = tblTreeTableSource.rows[rowIndex];
		var rowTarget = tblTreeTableTarget.insertRow(anchorIndex + 1);
		transferRow(rowTarget, rowSource);
		//Storing custom attributes.
		rowTarget.c_parentRow = anchorRow;
		anchorRow.c_childRows[0] = rowTarget;
		//Mark the row's childrens are loaded by setting the classname.
		anchorRow.c_loaded = "true";
		anchorRow.c_expanded = "true";
		showDescendents(anchorRow, name, tablePath, true);
		//Remove the Progress Bar set on the row expand state
		var image = parentDocument.getElementById(computeId2(EX_COL_IMG_PREFIX, name, tablePath, rowIndex));
		image.src = image.c_OpenImage;
	}
	updateVerticalTreeLine(tblTreeTableTarget);
	loadOver(window.parent.document);
}

function tbtr_removeColumnHeader(name, path, parentDocument, tblTreeTableTarget)
{
	var colHeaderRowId = computeId(COL_HEADER_ROW_ID_PREFIX, name, path);
	var colHeaderRow = parentDocument.getElementById(colHeaderRowId);
	if (colHeaderRow)
	{
		tblTreeTableTarget.tHead.deleteRow(colHeaderRow.rowIndex);
	}
}

function tbtr_hideColumnHeader(parentDocument, name, path, parentPath, rowPath)
{
	//alert("Row Id :: " + computeId2(NESTED_ROW_ID_PREFIX, name, parentPath, rowPath));
	var row = parentDocument.getElementById(computeId2(NESTED_ROW_ID_PREFIX, name, parentPath, rowPath));
	if (row.c_expanded == "false")
	{
		var colHeaderRowId = computeId(COL_HEADER_ROW_ID_PREFIX, name, path);
		var colHeaderRow = parentDocument.getElementById(colHeaderRowId);
		if (colHeaderRow)
		{
			colHeaderRow.style.display = "none";
		}
	}
}

function tbtr_transferRows2(tblTreeTableSource, tblTreeTableTarget)
{
	var index = tblTreeTableTarget.rows.length - 1;
	if (index != -1)
	{
		var row = tblTreeTableTarget.rows[index];
		if (row.c_type == "table_footer_row")
		{
			index -= 1;
		}
	}

	var rowIndex = 0;
	for (rowIndex=0; rowIndex<tblTreeTableSource.rows.length; rowIndex++)
	{
		var rowSource = tblTreeTableSource.rows[rowIndex];
		var rowTarget = tblTreeTableTarget.insertRow(index + rowIndex + 1);
		tbtr_transferRow(rowTarget, rowSource);
	}
}

function tbtr_removeDataRows(tableBody)
{
	var length = tableBody.rows.length;
	for (var rowIndex=0; rowIndex<length; rowIndex++)
	{
		var row = tableBody.rows[0];
		if (row.c_type != "table_footer_row")
		{
			tableBody.deleteRow(0);
		}
	}
}

function tbtr_transferRows(tblTreeTableSource, tblTreeTableTarget)
{
	var index = tblTreeTableTarget.rows.length - 1;
	var rowIndex = 0;
	for (rowIndex=0; rowIndex<tblTreeTableSource.rows.length; rowIndex++)
	{
		var rowSource = tblTreeTableSource.rows[rowIndex];
		var rowTarget = tblTreeTableTarget.insertRow(index + rowIndex + 1);
		tbtr_transferRow(rowTarget, rowSource);
	}
}

function tbtr_transferRow(rowTarget, rowSource)
{
	var colIndex = 0;
	for (colIndex=0; colIndex<rowSource.cells.length; colIndex++)
	{
		var cellSource = rowSource.cells[colIndex];
		var cellTarget = rowTarget.insertCell();
		cellTarget.mergeAttributes(cellSource);
		cellTarget.innerHTML = cellSource.innerHTML;
		cellTarget.id = cellSource.id;
	}
	rowTarget.mergeAttributes(rowSource);
	rowTarget.id = rowSource.id;
}

///transfer the Menus
function transferMenus()
{
	var sourceTags = document.body.children;
	if(sourceTags)
	{
		var parentDocument = window.parent.document;
		var i=0;
		for (; i<sourceTags.length; i++)
		{
			if(sourceTags[i].tagName == "DIV")
			{
				var menuPresent = parentDocument.getElementById(sourceTags[i].id);
				var bTransfer = false;
				if (menuPresent == null)
				{
					bTransfer = true;
				}
				else
				{
					 if (sourceTags[i].c_replaceold == "true")
					 {
				 		parentDocument.getElementById(sourceTags[i].id).removeNode(true);
						bTransfer = true;
					 }
				}

				if (bTransfer)
				{
					var newDiv=parentDocument.createElement("DIV");
					parentDocument.body.appendChild(newDiv);
					newDiv.mergeAttributes(sourceTags[i]);
					newDiv.id = sourceTags[i].id;
					newDiv.innerHTML = sourceTags[i].innerHTML;
				}
			}
		}
	}
}


function transferTree(name, tablePath, rowIndex)
{
	transferMenus();
	var tblTreeTableSource = document.getElementById(computeId(TABLE_CONTENT_ID, name, tablePath));
	transferTree2(name, tablePath, tblTreeTableSource, rowIndex);

	loadOver(window.parent.document);
}

function replaceTable(name, tablePath, rowPath)
{
	transferMenus();
	var tblTreeTableSource = document.getElementById(computeId(TABLE_CONTENT_ID, name, tablePath));
	var tdNestedCell = parentDocument.getElementById(computeId2(NESTED_CELL_ID, name, tablePath, subPath));
	tdNestedCell.innerHTML = tblTreeTableSource.outerHTML;
	loadOver(window.parent.document);
}

function transferTree2(name, tablePath, tblTreeTableSource, rowIndex)
{
	var parentDocument = window.parent.document;
	var tblTreeTableTarget = parentDocument.getElementById(computeId(TABLE_CONTENT_ID, name, tablePath));
	var anchorRow = parentDocument.getElementById(computeId2(NODE_ROW_PREFIX, name, tablePath, rowIndex));
	anchorRow.c_childRows = new Array(tblTreeTableSource.rows.length);

	if (tblTreeTableSource.rows.length == 0)
	{
		noChildrenFound(parentDocument, name, tablePath, rowIndex);
	}
	else
	{
		//Transfer the child rows.
		transferChildren(tblTreeTableSource, tblTreeTableTarget, anchorRow);

		updateVerticalTreeLine(tblTreeTableTarget);

		//Remove the Progress Bar set on the row expand state
		var image = parentDocument.getElementById(computeId2(EX_COL_IMG_PREFIX, name, tablePath, rowIndex));
		if (image.c_type == "progress")
		{
			image.src = image.c_OpenImage;
			image.c_type = "";
		}
	}

	//Mark the row's childrens are loaded by setting the classname.
	anchorRow.c_loaded = "true";
}

function transferChildren(tblTreeTableSource, tblTreeTableTarget, anchorRow)
{
	var index = anchorRow.rowIndex;
	var rowIndex = 0;
	for (rowIndex=0; rowIndex<tblTreeTableSource.rows.length; rowIndex++)
	{
		var rowSource = tblTreeTableSource.rows[rowIndex];
		var rowTarget = tblTreeTableTarget.insertRow(index + rowIndex + 1);
		transferRow(rowTarget, rowSource);

		//Storing custom attributes.
		rowTarget.c_parentRow = anchorRow;
		anchorRow.c_childRows[rowIndex] = rowTarget;
	}
}

function noChildrenFound(parentDocument, name, tablePath, rowPath)
{
	//Doesn't contain child nodes Hence just change the image
	var image = parentDocument.getElementById(computeId2(EX_COL_IMG_PREFIX, name, tablePath, rowPath));
	image.src = image.c_NoChildImage;

	var linkNode = parentDocument.getElementById(computeId2(EX_COL_LINK_PREFIX, name, tablePath, rowPath));
	linkNode.removeNode();
}

function transferRow(rowTarget, rowSource)
{
	var colIndex = 0;
	for (colIndex=0; colIndex<rowSource.cells.length; colIndex++)
	{
		var cellSource = rowSource.cells[colIndex];
		var cellTarget = rowTarget.insertCell();
		cellTarget.mergeAttributes(cellSource);
		cellTarget.innerHTML = cellSource.innerHTML;
		cellTarget.id = cellSource.id;
	}
	rowTarget.mergeAttributes(rowSource);
	try
	{
		rowTarget.c_expanded = rowSource.c_expanded;
		rowTarget.c_loaded = rowSource.c_loaded;
		rowTarget.c_type = rowSource.c_type;
	}
	catch(e)
	{
	}
	rowTarget.id = rowSource.id;
}

/*********************

END: Transfer from hidden frame to Main Frame related methods.

*************************/

/*********************

START: SELECT ROW/CELL RELATED Methods

*************************/
var SELECT_ROW = "0";
var SELECT_CELL = "1";
var SELECT_COLUMN = "2";
var SELECT_ALL = "3";

function SelectionObject(name, path, rowIndex, colIndex)
{
	var frmTable = document.getElementById(computeId3(FRAME_CONTENT_ID, name));
	//var table = document.getElementById(computeId(TABLE_CONTENT_ID, name, path));
	//this.table = table;
	this.name = name;
	this.path = path;
	this.rowIndex = rowIndex;
	this.colIndex = colIndex;
	if (!frmTable.c_selectionMode)
	{
		//alert("init selection mode");
		frmTable.c_selectionMode = SELECT_ROW;
	}
	this.selectionMode = frmTable.c_selectionMode;
	//this.selectionMode = SELECT_CELL;
	//table.c_selectionObject = this;

	this.getTable = getTable;

	this.doSelect = doSelect;

	this.doSelectRow = doSelectRow;
	this.doSelectColumn = doSelectColumn;
	this.doSelectCell = doSelectCell;
	this.doSelectAllThree = doSelectAllThree;


	this.selectRow = selectRow;
	this.selectTableRow = selectTableRow;
	this.selectTableRowHeader = selectTableRowHeader;

	this.selectCell = selectCell;

	this.selectColumn = selectColumn;
	this.selectTableColumn = selectTableColumn;
	this.selectTableColumnHeader = selectTableColumnHeader;

	this.lastRowIndex = -1;
	this.lastColIndex = -1;
	this.lastPath = this.path;

	this.SELECTION_COLOR = "#faf89a";

	return this;
}

function getTable(path)
{
	return document.getElementById(computeId(TABLE_CONTENT_ID, this.name, path));
}

function getSelectionObject(name, path, rowIndex, colIndex)
{
	var frmTable = document.getElementById(computeId3(FRAME_CONTENT_ID, name));
	try
	{
		var testField = frmTable.c_selectionObject.name;
		frmTable.c_selectionObject.name = name;
		frmTable.c_selectionObject.path = path;
		frmTable.c_selectionObject.rowIndex = rowIndex;
		frmTable.c_selectionObject.colIndex = colIndex;
		return frmTable.c_selectionObject;
	}
	catch(e)
	{
		frmTable.c_selectionObject = new SelectionObject(name, path, rowIndex, colIndex);
		return frmTable.c_selectionObject;
	}
}

function getSelectionObject3(name)
{
	var frmTable = document.getElementById(computeId3(FRAME_CONTENT_ID, name));
	try
	{
		var testField = frmTable.c_selectionObject.name;
		return frmTable.c_selectionObject;
	}
	catch(e)
	{
		return null;
	}

}
function getSelectionObject2(name, path, rowIndex, colIndex)
{
	var table = document.getElementById(computeId(TABLE_CONTENT_ID, name, path));
	try
	{
		var testField = table.c_selectionObject.name;
		table.c_selectionObject.rowIndex = rowIndex;
		table.c_selectionObject.colIndex = colIndex;
		return table.c_selectionObject;
	}
	catch(e)
	{
		return new SelectionObject(name, path, rowIndex, colIndex);
	}
}

function cell_Clicked(name, path, rowIndex, colIndex)
{
	var selectionObject = getSelectionObject(name, path, rowIndex, colIndex);
	selectionObject.doSelect();
}


function doSelect()
{

	switch (this.selectionMode) {
	   case SELECT_ROW :
	   		this.doSelectRow();
	       	break;
	   case SELECT_CELL :
	   		this.doSelectCell();
	       	break;
	   case SELECT_COLUMN :
	   		this.doSelectColumn();
	       	break;
	   case SELECT_ALL :
	   		this.doSelectAllThree();
	       	break;
	   default :
			this.doSelectRow();
			break;
	}

	this.lastPath = this.path;
	this.lastRowIndex = this.rowIndex;
	this.lastColIndex = this.colIndex;
}


function doSelectAllThree()
{
	this.doSelectRow();
	this.doSelectColumn();
}

function doSelectColumn()
{
	this.selectRow(this.lastRowIndex, this.lastPath, false);

	this.selectColumn(this.lastColIndex, this.lastPath, false);
	this.selectColumn(this.colIndex, this.path, true);
}

function selectColumn(colIndex, path, bSelect)
{
	this.selectTableColumnHeader(colIndex, path, bSelect);
	this.selectTableColumn(colIndex, path, bSelect);
}

function selectTableColumnHeader(colIndex, path, bSelect)
{
	if (colIndex != -1)
	{
		var colHeaderImage = document.getElementById(computeId2(COL_HDR_IMAGE, this.name, path, colIndex));
		if (colHeaderImage == null) return;
		if (bSelect)
		{
			colHeaderImage.src = colHeaderImage.c_SelectedImage;
		}
		else
		{
			colHeaderImage.src = colHeaderImage.c_UnSelectedImage;
		}
	}
}


function selectTableColumn(colIndex, path, bSelect)
{
	if (colIndex == -1) return;
	var rowIndex = 0;
	var loopIndex = 0;
	/*
		Anand [11/12/04 - the rowHeaderImage is null, when the user does now row menu clicking and then does
		group by and then removes group by
	*/
	var tableForPath = this.getTable(path);
	if (tableForPath != null){
		var rows = tableForPath.tBodies[0].rows;
		for (; loopIndex<rows.length; loopIndex++)
		{
			if (isTableRow2(rows[loopIndex]))
			{
				this.selectCell(rowIndex, colIndex, path, bSelect);
				rowIndex++;
			}
		}
	}
}


function doSelectRow()
{
	this.selectColumn(this.lastColIndex, this.lastPath, false);

	this.selectRow(this.lastRowIndex, this.lastPath, false);

	this.selectRow(this.rowIndex, this.path, true);
}


function selectRow(rowIndex, path, bSelect)
{
	this.selectTableRowHeader(rowIndex, path, bSelect);
	this.selectTableRow(rowIndex, path, bSelect);
}


function selectTableRowHeader(rowIndex, path, bSelect)
{
	if (rowIndex != -1)
	{
		var rowHeaderImage = getRowHeaderImage(this.name, path , rowIndex);
		/*
			Anand [11/12/04 - the rowHeaderImage is null, when the user does now row menu clicking and then does
			group by and then removes group by
		*/
		if (rowHeaderImage != null){
			if (bSelect)
			{
				rowHeaderImage.src = rowHeaderImage.c_SelectedImage;
			}
			else
			{
				rowHeaderImage.src = rowHeaderImage.c_UnSelectedImage;
			}
		}
	}
}

function selectTableRow(rowIndex, path, bSelect)
{
	if (rowIndex == -1) return;
	var colIndex = 0;
	var row = getRow(this.name, path, rowIndex);
	/*
		Anand [11/12/04 - the rowHeaderImage is null, when the user does now row menu clicking and then does
		group by and then removes group by
	*/
	if (row != null){
		if (!isTableRow2(row)) return;
		var cells = getCells(this.name, path, row);
		for (; colIndex<cells.length; colIndex++)
		{
			this.selectCell(rowIndex, colIndex, path, bSelect);
		}
	}
}

function doSelectCell()
{
	//Unselect any row/col if selected using row/col header with last selected row/col header
	this.selectRow(this.lastRowIndex, this.lastPath, false);
	this.selectColumn(this.lastColIndex, this.lastPath, false);

	//Un select last cell
	this.selectCell(this.lastRowIndex, this.lastColIndex, this.lastPath, false);

	//select cell
	this.selectCell(this.rowIndex, this.colIndex, this.path, true);

	//select associated row/col header
	this.selectTableRowHeader(this.rowIndex, this.path, true)
	this.selectTableColumnHeader(this.colIndex, this.path, true)
}

function selectCell(rowIndex, colIndex, path, bSelect)
{
	if (rowIndex != -1 && colIndex != -1)
	{
		var cellPath = rowIndex + PATH_SEPARATOR + colIndex;
		var cell = document.getElementById(computeId2(NODE_CELL_PREFIX, this.name, path, cellPath));
		if (cell)
		{
			if (bSelect)
			{
				cell.c_bgColor = cell.style.backgroundColor;
				cell.style.backgroundColor = this.SELECTION_COLOR;
			}
			else
			{
				cell.style.backgroundColor = cell.c_bgColor;
			}
		}
	}
}

function rowHeader_Clicked(name, path, rowIndex)
{
	var selectionObject = getSelectionObject(name, path, rowIndex, -1);
	selectionObject.doSelectRow();
	selectionObject.lastRowIndex = rowIndex;
	selectionObject.lastPath = path;
}


function colHeader_Clicked(name, path, colIndex)
{
	var selectionObject = getSelectionObject(name, path, -1, colIndex);
	selectionObject.doSelectColumn();
	selectionObject.lastColIndex = colIndex;
}
/*********************

END: SELECT ROW/CELL RELATED Methods

*************************/

/*********************

START: Util Methods

*************************/

function isTableRow(name, path, rowIndex)
{
	var row = document.getElementById(computeId2(NODE_ROW_PREFIX, name, path, rowIndex));
	return isTableRow2(row);
}

function isTableRow2(row)
{
	return (row.c_type == TYPE_ROW);
}


/*********************

END: Util Methods

*************************/

/*********************

START: FILTER RELATED METHODS

*************************/
function filterInput_KeyDown(name, tablePath, colIndex)
{
	if (event.keyCode == 13)
	{
		doFilter(name, tablePath, colIndex);
	}
}

function showAllRows(rows)
{
	var rowIndex = 0;
	for (; rowIndex < rows.length; rowIndex++)
	{
		rows[rowIndex].style.display = "";
	}
}


function doFilter(name, tablePath, numColumns)
{
	var colIndex = 0;

	var table = document.getElementById(computeId(TABLE_CONTENT_ID, name, tablePath));
	var rowsToFilter = table.tBodies[0].rows;

	showAllRows(rowsToFilter);

	for(; colIndex < numColumns; colIndex++)
	{
		var filterInput = document.getElementById(computeId2(FILTER_INPUT_ID_PREFIX, name, tablePath, colIndex));
		doColumnFilter(rowsToFilter, colIndex, filterInput);
	}
	updateExpandCollapseImages(table);
}

function doColumnFilter(rowsToFilter, colIndex, filterInput)
{
	if (filterInput.value.length <= 0)
	{
		return;
	}

	var rowIndex = 0;
	for (; rowIndex < rowsToFilter.length; rowIndex++)
	{
		var row = rowsToFilter[rowIndex];
		if (isTableRow2(row) && row.style.display == "")
		{
			var cellValue = row.cells[COLUMN_OFFSET + colIndex].c_cellvalue;
			if (cellValue.indexOf(filterInput.value) <= -1)
			{
				row.style.display = "none";
			}
		}
	}
}

/*********************

END: FILTER RELATED METHODS

*************************/

/*********************

START: COLUMN RESIZE METHODS

*************************/
var startX = -1;
var bResizeStart = false;
var MIN_COLUMN_WIDTH = 5;
var MOVE_RIGHT = 0;
var MOVE_LEFT = 1;
var MOVE_NONE = -1;

var mouseDownTaskMode;
var TASKMODE_RESIZE = 1;
var TASKMODE_SORT= 2;

var js_moveDir = MOVE_NONE;

function sortColumn_mouseDown()
{
	mouseDownTaskMode = TASKMODE_SORT;
	return true;
}
function column_mouseEnter(name, path, moveDir, colIndex)
{
	setResizeCursor(name, path, moveDir, colIndex);
}

function column_mouseOver(name, path, moveDir, colIndex)
{
	setResizeCursor(name, path, moveDir, colIndex);
}

function column_mouseOut(name, path, moveDir, colIndex)
{
	resetCursor(name, path, moveDir, colIndex);

}

function resizeColumn(name, path, moveDir, colIndex){
	if(bResizeStart){
		var endX = event.x;
		var movedBy = endX - startX;

		if(moveDir == MOVE_RIGHT){
			colIndex--;
		}
		var thLeft = document.getElementById(computeId2(COL_HDR_TH_ID_PREFIX, name, path,colIndex));
		var newWidthLeft = thLeft.offsetWidth + movedBy;



		var thRight = document.getElementById(computeId2(COL_HDR_TH_ID_PREFIX, name, path,colIndex+1));
		var newWidthRight = thRight.offsetWidth - movedBy;

		try {
			if (newWidthRight > MIN_COLUMN_WIDTH){
				var col = document.getElementById(computeId2(COL_ID_PREFIX, name, path, colIndex+1));
				col.style.width = newWidthRight;
			}

			if (newWidthLeft > MIN_COLUMN_WIDTH){
				var col = document.getElementById(computeId2(COL_ID_PREFIX, name, path, colIndex));
				col.style.width = newWidthLeft;
			}
		}catch(e){

		}

	}
		bResizeStart = false;
}


function column_mouseUp(name, path, moveDir, colIndex)
{
	if(bResizeStart==true)
	{
		resetCursor(name, path, moveDir, colIndex);
		resizeColumn(name, path, moveDir, colIndex);
		try
		{
			document.releaseCapture();
		}
		catch(e)
		{
			//alert(e);
		}
		bResizeStart = false;
	}


}


function column_mouseDown(name, path, moveDir, colIndex)
{
	setResizeCursor(name, path, moveDir, colIndex);
	bResizeStart = true;
	startX = event.x;
	mouseDownTaskMode = TASKMODE_RESIZE;
	try
	{
		event.srcElement.setCapture();
	}
	catch(e)
	{
		//alert(e);
	}
}

function setResizeCursor(name, path, moveDir, colIndex)
{
	var cell = event.srcElement;
	if (moveDir == MOVE_RIGHT)
	{
		cell.style.cursor = "w-resize";
	}
	else
	{
		cell.style.cursor = "e-resize";
	}
}

function resetCursor(name, path, moveDir, colIndex)
{
	var cell = event.srcElement;
	cell.style.cursor = "default";
}



/*function getCol(name, path, index)
{
	return

	COL_ID_PREFIX
	tableTree_COLUMN*events_0*0
	var tblTree = document.getElementById(computeId(TABLE_CONTENT_ID, name, path));
	var colgroup = tblTree.getElementsByTagName("COLGROUP")(0);
	return colgroup.children(COLUMN_OFFSET + index);
}*/

/*********************

END: COLUMN RESIZE METHODS

*************************/



/*********************

START: DATA GET/SET METHODS

*************************/
//return the 2 dimension data array
function getNumColumns(name, path)
{
	var table = getTreeTable(name, path);
	return parseInt(table.c_numColumns);
}

function traverseTableTree(name, path, processDataFunc)
{
	if (path == null)
	{
		path = "0";
	}

	var table = getTreeTable(name,path);
	if (table != null)
	{
		var tableData = getTableData(name, path);
		var columnHeaderData = getColumnHeaderData(name, path);
		var headerData = getHeaderData(name, path);
		eval(processDataFunc + "(name, path, tableData, columnHeaderData, headerData)");
		for (var rowIndex=0; rowIndex<tableData.length; rowIndex++)
		{
			var nestedTableIndex = 0;
			while(true)
			{
				var nestedTablePath = path + PATH_SEPARATOR + rowIndex + PATH_SEPARATOR + nestedTableIndex;
				var bSuccess = traverseTableTree(name, nestedTablePath, processDataFunc);
				if (!bSuccess)
				{
					//No more nested Tables at path nestedTablePath
					break;
				}
			}
		}
		return true;
	}
	return false;
}

function getHeaderData(name, path)
{
	var headerCell = document.getElementById(computeId(HEADER_TEXT_ID_PREFIX, name, path));
	return headerCell.innerText;
}

function getTableData(name,path)
{
	var table = getTreeTable(name,path);
	var rows = table.tBodies[0].rows;
	var table_data = new Array(rows.length);
	for (var i=0; i< rows.length; i++)
	{
		if (row.c_type == TYPE_FOOTER_ROW)
		{
			break;
		}
		table_data[i] = getRowData2(name, path, rows[i]);
	}
	return table_data;
}

function getRowData(name,path, rowIndex)
{
	var row = getRow(name,path, rowIndex);
	return getRowData2(name, path, row);
}


function getRowData2(name, path, row)
{

	var cells = getCells(name, path, row);
	var row_data = new Array(cells.length);
	for (var i=0; i< cells.length; i++)
	{
		row_data[i] = cells[i].c_cellvalue;
	}
	return row_data;
}

function getCellData(name,path, rowIndex, colIndex)
{
	var row = getRow(name,path, rowIndex);
	if (isTableExpandCollapseCell(name, path))
	{
		return row.cells[COLUMN_OFFSET + colIndex].c_cellvalue;
	}
	else
	{
		return row.cells[COLUMN_OFFSET + colIndex - 1].c_cellvalue;
	}
}

function getColumnData(name,path, colIndex)
{
	var table = getTreeTable(name,path);
	var rows = table.tBodies[0].rows;
	var column_data = new Array(rows.length);
	for (var i=0; i< rows.length; i++)
	{
		column_data[i] = rows[i].cells[COLUMN_OFFSET + colIndex].c_cellvalue;
	}
	return column_data;
}


function setColumnData(name,path, colIndex, arr_columData)
{
	var table = getTreeTable(name,path);
	var rows = table.tBodies[0].rows;
	for (var i=0; i< rows.length; i++)
	{
		rows[i].cells[COLUMN_OFFSET + colIndex].innerText = arr_columData[i];
		rows[i].cells[COLUMN_OFFSET + colIndex].c_cellvalue = arr_columData[i];
	}
	return;
}

function getColumnHeaderData(name, path)
{
	var table = getTreeTable(name,path);
	var num_column = parseInt(table.c_numColumns);
	var column_header_array = new Array();
	for ( var colIndex = 0 ; colIndex < num_column; colIndex++)
	{
		column_header_array[colIndex] = getColumnHeaderTD (name, path , colIndex).innerText;
	}
	return column_header_array;
}

function setColumnHeaderData(name, path, arr_column_header)
{
	var table = getTreeTable(name,path);
	var num_column = parseInt(table.c_numColumns);
	for ( var colIndex = 0 ; colIndex < num_column; colIndex++)
	{
		getColumnHeaderTD (name, path , colIndex).innerText = arr_column_header[colIndex];
	}
	return;
}

function setCellData(name,path, rowIndex, colIndex, value)
{
			var row = getRow(name,path, rowIndex);
			row.cells[COLUMN_OFFSET + colIndex].c_cellvalue = value;
			row.cells[COLUMN_OFFSET + colIndex].innerText = value;
			row.cells[COLUMN_OFFSET + colIndex].title = value;
}

function setRowData(name,path, rowIndex, arr_rowData)
{
	var row = getRow(name, path, rowIndex);
	setRowData2(name,path, row, arr_rowData);
	return;
}

function setRowData2(name,path, row, arr_rowData)
{
	var cells = getCells(name, path, row);
	for (var i=0; i< cells.length; i++)
	{
		cells[i].innerText = arr_rowData[i];
		cells[i].c_cellvalue = arr_rowData[i];
	}
	return;
}

function setTableData(name, path, arr_tableData)
{
	var table = getTreeTable(name,path);
	var rows = table.tBodies[0].rows;
	for (var i=0; i< rows.length; i++)
	{
		setRowData2(rows[i], arr_tableData[i]);
	}
	return;
}

function testData(name,path,rowIndex,colIndex)
{

	var tbl_arr = getTableData(name,path);
	var row_arr = getRowData(name,path,rowIndex);
	var cell_value = getCellData(name,path,rowIndex,colIndex);
	var column_arr = getColumnData(name,path,colIndex);
	var column_header_arr = getColumnHeaderData(name,path);
}

function getRowAttribute(name,path, rowIndex, attribute)
{
	var row = getRow(name,path, rowIndex);
	return eval("row.c_" + attribute);
}

/*********************

END: DATA GET/SET METHODS

*************************/

/*********************

START: EXPAND/COLLAPSE IMAGE UPDATE

*************************/
var SINGLE_ITEM_NODE = 0;
var SINGLE_OPEN_NODE = 1;
var SINGLE_CLOSE_NODE = 2;
var LAST_ITEM_NODE = 3;
var LAST_OPEN_NODE = 4;
var LAST_CLOSE_NODE = 5;
var FIRST_ITEM_NODE = 6;
var FIRST_OPEN_NODE = 7;
var FIRST_CLOSE_NODE = 8;
var ONLY_ITEM_NODE = 9;
var ONLY_OPEN_NODE = 10;
var ONLY_CLOSE_NODE = 11;

//Open-Close-No Child Image Indices.
var INDEX_CLOSE_IMAGE = 0;
var INDEX_OPEN_IMAGE = 1;
var INDEX_NOCHILD_IMAGE = 2;

function updateExpandCollapseImages(table)
{
	var rows = table.tBodies[0].rows;
	for (var i=0; i<rows.length; i++)
	{
		var row = rows[i];
		if (row.c_type == TYPE_GROUP_ROW || row.c_type == TYPE_NESTED_TABLE_ROW)
		{
			//Row is a group row. Skip it.
			continue;
		}
		if (row.c_type == "table_footer_row")
		{
			//Row is a group row. Skip it.
			continue;
		}
		var exColCell = row.cells[0];
		var exColImgNode = exColCell.getElementsByTagName("IMG")(0);
		var isFirst = exColImgNode.c_first == "true";
		var isLast = exColImgNode.c_last == "true";
		var isNewFirst = false;//(i==0);
		var isNewLast = isLastTableRow(i, rows);


		if ((isFirst != isNewFirst) || (isLast != isNewLast))
		{
			updateImage(table, row, exColImgNode, isNewFirst, isNewLast);
			if (isLast)
			{
				removeVerticalTreeLine(rows, row, i);
			}
			if (!isNewLast)
			{
				setVerticalTreeLine(rows, row, i);
			}
			else
			{
				removeVerticalTreeLine(rows, row, i);
			}
		}
	}
}

function loadExpandCollapseImages(table, isFirst,isLast)
{
	var lc_closeImage = "";
	var lc_openImage = "";
	var lc_noChildImage = "";

	if (isFirst) {
		if (isLast) {
			lc_closeImage = getExpandCollapseImage(table, SINGLE_CLOSE_NODE);
			lc_openImage = getExpandCollapseImage(table, SINGLE_OPEN_NODE);
			lc_noChildImage = getExpandCollapseImage(table, SINGLE_ITEM_NODE);
		} else {
			lc_closeImage = getExpandCollapseImage(table, FIRST_CLOSE_NODE);
			lc_openImage = getExpandCollapseImage(table, FIRST_OPEN_NODE);
			lc_noChildImage = getExpandCollapseImage(table, FIRST_ITEM_NODE);
		}
	} else {

		if (isLast) {

			lc_closeImage = getExpandCollapseImage(table, LAST_CLOSE_NODE);
			lc_openImage = getExpandCollapseImage(table, LAST_OPEN_NODE);
			lc_noChildImage = getExpandCollapseImage(table, LAST_ITEM_NODE);
		} else {

			lc_closeImage = getExpandCollapseImage(table, ONLY_CLOSE_NODE);
			lc_openImage = getExpandCollapseImage(table, ONLY_OPEN_NODE);
			lc_noChildImage = getExpandCollapseImage(table, ONLY_ITEM_NODE);
		}
	}
	var imgExpandCollapse = new Array(3);

	imgExpandCollapse[INDEX_CLOSE_IMAGE] = lc_closeImage;
	imgExpandCollapse[INDEX_OPEN_IMAGE] = lc_openImage;
	imgExpandCollapse[INDEX_NOCHILD_IMAGE] = lc_noChildImage;

	return imgExpandCollapse;
}

function getExpandCollapseImage(table, imgIndex)
{
	eval("var imgSrc = table.excolimg_" + imgIndex);
	return imgSrc;
}


function updateImage(table, row, imgNode, isFirst,isLast)
{
	var newImages = loadExpandCollapseImages(table, isFirst,isLast);
	imgNode.c_first = "" + isFirst;
	imgNode.c_last = "" + isLast;
	imgNode.c_OpenImage = newImages[INDEX_OPEN_IMAGE];
	imgNode.c_CloseImage = newImages[INDEX_CLOSE_IMAGE];
	imgNode.c_NoChildImage = newImages[INDEX_NOCHILD_IMAGE];
	if (row.c_expanded == "true")
	{
		if (imgNode.src.indexOf("treeItem") > -1)
		{
			imgNode.src = imgNode.c_NoChildImage;
		}
		else
		{
			imgNode.src = imgNode.c_OpenImage;
		}
	}
	else
	{
		imgNode.src = imgNode.c_CloseImage;
	}
}

function isLastTableRow(rowIndex, rows)
{
	if (rowIndex == rows.length) return true;

	rowIndex++;
	for (; rowIndex < rows.length; rowIndex++)
	{
		var row = rows[rowIndex];
		if (row.c_type == TYPE_ROW)
		{
			break;
		}
	}
	return (rowIndex == rows.length);
}

/*********************

END: EXPAND/COLLAPSE IMAGE UPDATE

*************************/

/*********************

START: TREE LINE METHODS

*************************/

function updateVerticalTreeLine(table)
{
	var rows = table.tBodies[0].rows;
	var row = null;
	var rowIndex = rows.length-1;
	for (; rowIndex>=0; rowIndex--)
	{
		row = rows[rowIndex];
		if (row.c_type == TYPE_ROW)
		{
			break;
		}
		row = null;
	}
	if (row == null) return; //No Table row in table.

	removeVerticalTreeLine(rows, row, rowIndex);
}

function removeVerticalTreeLine(rows, row, rowIndex)
{
	var groupRowIndex = rowIndex + 1;
	for (; groupRowIndex < rows.length; groupRowIndex++)
	{
		var groupRow = rows[groupRowIndex];
		if (groupRow.c_type == TYPE_GROUP_ROW)
		{
			groupRow.cells[0].className="";
		}
		else
		{
			break;
		}
	}

}

function setVerticalTreeLine(rows, row, rowIndex)
{
	var groupRowIndex = rowIndex + 1;
	for (; groupRowIndex < rows.length; groupRowIndex++)
	{
		var groupRow = rows[groupRowIndex];
		if (groupRow.c_type == TYPE_GROUP_ROW)
		{
			groupRow.cells[0].className="treeCellLine";
		}
		else
		{
			break;
		}
	}
}

/*********************

END: TREE LINE METHODS

*************************/
/********************************
//Begin Header Hover Code
***********************************/
function Header_OnMouseOver(name, path)
{
	var headerImage = document.getElementById(computeId(HEADER_IMG_ID_PREFIX, name, path));
	headerImage.src = headerImage.c_UnSelectedHoverImage;
}

function Header_OnMouseOut(name, path)
{
	var headerImage = document.getElementById(computeId(HEADER_IMG_ID_PREFIX, name, path));
	headerImage.src = headerImage.c_UnSelectedImage;
}

function Header_OnMouseDown(name, path)
{
	var headerImage = document.getElementById(computeId(HEADER_IMG_ID_PREFIX, name, path));
	headerImage.src = headerImage.c_UnSelectedDownImage;
}

/********************************
//End Header Hover Code
***********************************/


/********************************
//Begin Row Hover Code
***********************************/
var ROW_MOUSE_NORMAL = 0;
var ROW_MOUSE_HOVER = 1;
var ROW_MOUSE_DOWN = 2;

function onrowHeader_MouseOver(name, path, rowIndex)
{
	hoverTableRowHeader(name, path, rowIndex, ROW_MOUSE_HOVER);
}

function onrowHeader_MouseOut(name, path, rowIndex)
{
	hoverTableRowHeader(name, path, rowIndex, ROW_MOUSE_NORMAL);
}

function onrowHeader_MouseDown(name, path, rowIndex)
{
	hoverTableRowHeader(name, path, rowIndex, ROW_MOUSE_DOWN);
}

function hoverTableRowHeader(name, path, rowIndex, row_mouse_mode)
{
	if (rowIndex != -1)
	{
		var rowHeaderImage = getRowHeaderImage(name, path , rowIndex);
		var selectionObject = getSelectionObject3(name);
		var bSelect =false;
		if(selectionObject !=null)
		{
			if(selectionObject.lastPath == path && selectionObject.lastRowIndex==rowIndex)
			{
				bSelect = true;
			}
		}
		if (bSelect)
		{
			switch(row_mouse_mode){
				case ROW_MOUSE_HOVER:
					rowHeaderImage.src = rowHeaderImage.c_SelectedHoverImage;
					break;
				case ROW_MOUSE_DOWN:
					rowHeaderImage.src = rowHeaderImage.c_SelectedDownImage;
					break;
				default:
					rowHeaderImage.src = rowHeaderImage.c_SelectedImage;
					break;
			}
		}
		else
		{
			switch(row_mouse_mode){
				case ROW_MOUSE_HOVER:
					rowHeaderImage.src = rowHeaderImage.c_UnSelectedHoverImage;
					break;
				case ROW_MOUSE_DOWN:
					rowHeaderImage.src = rowHeaderImage.c_UnSelectedDownImage;
					break;
				default:
					rowHeaderImage.src = rowHeaderImage.c_UnSelectedImage;
					break;
			}

		}
	}
}

//New Functions

function refreshTable(name, path, url)
{
	if (path == "0")
	{
		//window.location.href = url;
		postRequest(url, "_self");
		return;
	}
	var tableContainerCell = document.getElementById(computeId(NESTED_TD_ID_PREFIX, name, path));
	var rowContainerCell = tableContainerCell.parentElement;

	var expanded = rowContainerCell.c_expanded;
	var loaded = rowContainerCell.c_loaded;

	/*if (loaded == "true" && expanded != "true")
	{
		rowContainerCell.c_loaded = "false";
	}*/

	var frameId = computeId3(FRAME_CONTENT_ID, name);
	//document.frames[frameId].location.href = url + "&loaded="+loaded+"&expanded="+expanded;
	postRequest(url + "&loaded="+loaded+"&expanded="+expanded);
	loadStart();
}

//New Functions

function transferTable(name, path)
{
	transferMenus();
	var tblTreeTableSource = document.getElementById(computeId(TABLE_CONTENT_ID, name, path));
	var parentDocument = window.parent.document;
	var tableContainerCell = parentDocument.getElementById(computeId(NESTED_TD_ID_PREFIX, name, path));
	tableContainerCell.innerHTML = tblTreeTableSource.outerHTML;
	loadOver(parentDocument);
}

function transferGroupRows(name, path, parentPath, rowPath)
{
	transferMenus();
	var tblTreeTableSource = document.getElementById(computeId(TABLE_CONTENT_ID, name, path));
	var parentDocument = window.parent.document;
	var tblTreeTableTarget = parentDocument.getElementById(computeId(TABLE_CONTENT_ID, name, path));
	tbtr_removeRows(tblTreeTableTarget.tBodies[0]);
	tbtr_transferRows(tblTreeTableSource.tBodies[0], tblTreeTableTarget.tBodies[0]);
	var colHeaderRowId = computeId(COL_HEADER_ROW_ID_PREFIX, name, path);
	var colHeaderRow = parentDocument.getElementById(colHeaderRowId);
	if (colHeaderRow)
	{
		tblTreeTableTarget.tHead.deleteRow(colHeaderRow.rowIndex);
	}
	//Remove the Progress Bar set on the row expand state
	removeProgressBar(parentDocument, name, parentPath, rowPath);

	runCommands();
	loadOver(parentDocument);
}

function transferNextRowSet(name, path)
{
	transferMenus();
	var tblTreeTableSource = document.getElementById(computeId(TABLE_CONTENT_ID, name, path));
	var parentDocument = window.parent.document;
	var tblTreeTableTarget = parentDocument.getElementById(computeId(TABLE_CONTENT_ID, name, path));

	tbtr_removeDataRows(tblTreeTableTarget.tBodies[0]);
	tbtr_transferRows2(tblTreeTableSource.tBodies[0], tblTreeTableTarget.tBodies[0]);
	runCommands();
	loadOver(parentDocument);
	/*var row = parentDocument.getElementById(computeId("FOOTER_ROW", name, path));
	if (row != null)
	{
		var oldScrollLeft = parentDocument.body.scrollLeft;
		row.scrollIntoView(false);
		parentDocument.body.scrollLeft = oldScrollLeft;
	}*/
}

function transferPrevRowSet(name, path)
{
	transferMenus();
	var tblTreeTableSource = document.getElementById(computeId(TABLE_CONTENT_ID, name, path));
	var parentDocument = window.parent.document;
	var tblTreeTableTarget = parentDocument.getElementById(computeId(TABLE_CONTENT_ID, name, path));

	tbtr_removeDataRows(tblTreeTableTarget.tBodies[0]);
	tbtr_transferRows2(tblTreeTableSource.tBodies[0], tblTreeTableTarget.tBodies[0]);
	runCommands();
	loadOver(parentDocument);
	/*var row = parentDocument.getElementById(computeId("FOOTER_ROW", name, path));
	if (row != null)
	{
		var oldScrollLeft = parentDocument.body.scrollLeft;
		row.scrollIntoView(false);
		parentDocument.body.scrollLeft = oldScrollLeft;
	}*/
}

function transferAppendRowSet(name, path)
{
	transferMenus();
	var tblTreeTableSource = document.getElementById(computeId(TABLE_CONTENT_ID, name, path));
	var parentDocument = window.parent.document;
	var tblTreeTableTarget = parentDocument.getElementById(computeId(TABLE_CONTENT_ID, name, path));

	tbtr_transferRows2(tblTreeTableSource.tBodies[0], tblTreeTableTarget.tBodies[0]);
	runCommands();
	loadOver(parentDocument);
	/*var row = parentDocument.getElementById(computeId("FOOTER_ROW", name, path));
	if (row != null)
	{
		var oldScrollLeft = parentDocument.body.scrollLeft;
		row.scrollIntoView(false);
		parentDocument.body.scrollLeft = oldScrollLeft;
	}*/
}



function tbtr_removeRows(tBody)
{
	var length = tBody.rows.length;
	for (var rowIndex=0; rowIndex<length; rowIndex++)
	{
		tBody.deleteRow(0);
	}
}

function fireGroupByRequest(name, path, parentPath, rowPath, tableExpandUrl, groupByURL, groupByFieldName, groupByFieldParam)
{
	var url = addGroupBy(name, path, groupByURL, groupByFieldName, groupByFieldParam, tableExpandUrl, parentPath, rowPath);
	if (path != "0")
	{
		try
		{
			markExpanded(NESTED_ROW_ID_PREFIX, name, parentPath, rowPath);
		}
		catch(e)
		{
		}
	}
	fireRequest(name, path, url, true);
}

function fireRequest(name, path, url, targetHiddenFrame)
{
	if (!targetHiddenFrame)
	{
		postRequest(url, "_self");//window.location.href = url;
		return;
	}
	var frameId = computeId3(FRAME_CONTENT_ID, name);
	postRequest(url);
	//document.frames[frameId].location.href = url;
	loadStart();
}

function markExpanded(rowPrefix, name, tablePath, rowPath)
{
	var row = document.getElementById(computeId2(rowPrefix, name, tablePath, rowPath));
	var image = document.getElementById(computeId2(EX_COL_IMG_PREFIX, name, tablePath, rowPath));
	var linkNode = document.getElementById(computeId2(EX_COL_LINK_PREFIX, name, tablePath, rowPath));

	row.c_loaded = "true";
	row.c_expanded = "true";
	image.src = image.c_OpenImage;
	linkNode.href = "javascript://";
}

var HEADER_ID_PREFIX = "tableTree_TABLE_HEADER";
var INNER_HEADER_ROW_ID_PREFIX = HEADER_ID_PREFIX + "_INNER_HEADER_ROW";
var GROUP_BY_START_ID_PREFIX = HEADER_ID_PREFIX + "_GROUP_BY_START";
var GROUP_BY_END_ID_PREFIX = HEADER_ID_PREFIX + "_GROUP_BY_END";
var HEADER_TEXT_ID_PREFIX = HEADER_ID_PREFIX + "_TEXT";
var HEADER_IMG_ID_PREFIX = HEADER_ID_PREFIX + "_IMG";

function addGroupBy(name, path, groupByURL, groupByFieldText, groupByFieldParam, tableExpandUrl, parentPath, rowPath)
{
	var table = getTreeTable(name, path);
	table.c_groupByUrl = groupByURL;
	table.c_tableExpandUrl = tableExpandUrl;

	addGroupByCell(name, path, groupByFieldText, groupByFieldParam, parentPath, rowPath);

	addToTableGroupByList(table, groupByFieldText);

	var url = getGroupByUrl(name, path);//groupByURL + "&" + groupByFieldParam;
	return url;

}

function addGroupByCell(name, path, groupByFieldText, groupByFieldParam, parentPath, rowPath)
{
	var table = getTreeTable(name, path);
	var row = document.getElementById(computeId(INNER_HEADER_ROW_ID_PREFIX, name, path));

	var imagePath = table.c_imagePath;
	var leftImageSrc = "groupbyfieldleft_up.gif";
	var rightImageSrc = "groupbyfieldright_up.gif";


	var endCell = document.getElementById(computeId(GROUP_BY_END_ID_PREFIX, name, path));

	var startIndex = endCell.cellIndex;
	var leftCell = row.insertCell(startIndex);
	leftCell.innerHTML = createLeftImageTagHTML(imagePath, leftImageSrc, 9	);
	leftCell.id = computeId2("GROUP_BY", name, path, groupByFieldText + "0");


	startIndex++;
	var titleCell = row.insertCell(startIndex);
	titleCell.className = "tbtr_groupByFieldText";
	titleCell.innerHTML = groupByFieldText;
	titleCell.id = computeId2("GROUP_BY", name, path, groupByFieldText + "1");
	titleCell.c_groupByField = groupByFieldText;
	titleCell.c_groupByParam = groupByFieldParam;

	startIndex++;
	var removeHandler = "return removeGroupBy('" + name + "', '" + path + "', '" + groupByFieldText  + "', '" + parentPath  + "', '" + rowPath + "');";
	var rightCell = row.insertCell(startIndex);
	rightCell.innerHTML = createRightImageTagHTML(imagePath, rightImageSrc, 16, removeHandler);
	rightCell.id = computeId2("GROUP_BY", name, path, groupByFieldText + "2");
	rightCell.style.cursor = "hand";

	startIndex++;
	var spacerCell = row.insertCell(startIndex);
	spacerCell.className = "tbtr_groupByTextTD";
	spacerCell.style.width = "5px";
	spacerCell.innerHTML = "&nbsp;";
	spacerCell.id = computeId2("GROUP_BY", name, path, groupByFieldText + "3");
}

function addToTableGroupByList(table, groupByFieldText)
{
	if (table.c_groupByList == undefined)
	{
		table.c_groupByList = new Array();
	}
	table.c_groupByList[table.c_groupByList.length] = groupByFieldText;
}

function removeFromTableGroupByList(table, groupByFieldText)
{
	if (table.c_groupByList == undefined) return;

	var newGroupList = new Array();

	for (var index=0; index<table.c_groupByList.length; index++)
	{
		if (table.c_groupByList[index] != groupByFieldText)
		{
			newGroupList[newGroupList.length] = table.c_groupByList[index];
		}
	}

	table.c_groupByList = newGroupList;
}

function removeGroupBy(name, path, groupByFieldText, parentPath, rowPath)
{
	var row = document.getElementById(computeId(INNER_HEADER_ROW_ID_PREFIX, name, path));
	for ( var i=0; i<4; i++)
	{
		var cell = document.getElementById(computeId2("GROUP_BY", name, path, groupByFieldText + i));
		row.deleteCell(cell.cellIndex);
	}

	var table = getTreeTable(name, path);
	removeFromTableGroupByList(table, groupByFieldText);
	var url = getGroupByUrl(name, path);

	if (table.c_tableExpandUrl == url && path == "0")
	{
		fireRequest(name, path, url, false);
	}
	else
	{
		fireRequest(name, path, url, true);
	}

	if (path != "0")
	{
		var linkNode = document.getElementById(computeId2(EX_COL_LINK_PREFIX, name, parentPath, rowPath));
		linkNode.href = url;
	}

	var menuId = computeId("GROUP_BY_MENU", name, path);
	enableMenuItem(menuId, groupByFieldText);
	var imgId = "tableTree_TABLE_HEADER_GROUP_BY_IMG*" + name + "_" + path;
	var img = document.getElementById(imgId);
	img.c_enabled = "true";
	img.src = img.c_enabled_src;
}

function getGroupByUrl(name, path)
{
	var table = getTreeTable(name, path);
	if (table.c_groupByList == undefined)
	{
		return table.c_tableExpandUrl;
	}
	if (table.c_groupByList.length == 0)
	{
		return table.c_tableExpandUrl;
	}

	var groupByURL = table.c_groupByUrl;

	for (var index=0; index<table.c_groupByList.length; index++)
	{
		var titleCell = document.getElementById(computeId2("GROUP_BY", name, path, table.c_groupByList[index] + 1));
		if (index > 0)
		{
			var params = titleCell.c_groupByParam.split("&", 2);
			var parts = params[0].split("=");
			groupByURL += "&" + parts[0] + PATH_SEPARATOR + (index - 1) + "=" + parts[1];
			if (params[1] != undefined)
			{
			 	groupByURL +=  "&" + params[1];
			 }
		}
		else
		{
			groupByURL += "&" + titleCell.c_groupByParam;
		}
	}
	return groupByURL;
}


function createLeftImageTagHTML(imagePath, imageSrc, width	)
{
	var src = imagePath + "/" + imageSrc;
	return "<img src=\"" + src + "\" border=\"0\" width=\"" + width + "\" height=\"21\"/>";
}

function createRightImageTagHTML(imagePath, imageSrc, width, onclickHandler)
{
	var src = imagePath + "/" + imageSrc;
	var html = "<img src=\"" + src + "\" border=\"0\" width=\"" + width + "\" height=\"21\"";
	html += " onclick=\"" + onclickHandler + "\" />";
	return html;
}


///Commands

function addGroupByField(name, path, fieldName)
{
	var parentDocument = window.parent.document;
	var tblTreeTableTarget = parentDocument.getElementById(computeId(TABLE_CONTENT_ID, name, path));
	addToTableGroupByList(tblTreeTableTarget, fieldName);
	var menuId = computeId("GROUP_BY_MENU", name, path);
	window.parent.disableMenuItem(window, menuId, fieldName);
}

function enableGroupByMenuItem(menuId, fieldName)
{
	try
	{
		window.parent.enableMenuItem(menuId, fieldName);
	}
	catch(e)
	{
	}
}


function showAppendRowSet(name, path, url)
{
	var footerAppendButtonCell = document.getElementById(computeId("FOOTER_APPEND_BUTTON_CELL", name, path));
	if (footerAppendButtonCell.c_enable == "false")
	{
		return;
	}
	if (footerAppendButtonCell.innerText == "All Shown")
	{
		alert("All are shown");
		return;
	}
	var footerRow = document.getElementById(computeId("FOOTER_ROW", name, path));
	if (footerRow.c_dispcount != undefined)
	{
		url += "&dispcount=" + footerRow.c_dispcount;
	}
	fireRequest(name, path, url, true);
}

function showNextRowSet(name, path, url)
{
	/*var footerNextButtonCell = document.getElementById(computeId("FOOTER_NEXT_BUTTON_CELL", name, path));
	if (footerNextButtonCell.c_disable == "true")
	{
		alert("No more next");
		return;
	}*/
	fireRequest(name, path, url, true);
}

function showPrevRowSet(name, path, url)
{
	/*var footerPrevButtonCell = document.getElementById(computeId("FOOTER_PREV_BUTTON_CELL", name, path));
	if (footerPrevButtonCell.c_disable == "true")
	{
		alert("No more previous");
		return;
	}*/
	fireRequest(name, path, url, true);
}

function updateFooterInAppendMode(name, path, totalCount, currentCount, showAllThreshold)
{
	var parentDocument = window.parent.document;

	var footerRow = parentDocument.getElementById(computeId("FOOTER_ROW", name, path));
	if (totalCount <= currentCount)
	{
		//All rows displayed. Hide the footer now.
		footerRow.style.display = "none";
	}

	footerRow.c_dispcount = currentCount;

	var footerInfoCell = parentDocument.getElementById(computeId("FOOTER_INFO_CELL", name, path));
	if (totalCount <= currentCount)
	{
		footerInfoCell.innerText =  "Displaying All";
	}
	else
	{
		footerInfoCell.innerText =  "Displaying " + currentCount + " of " + totalCount;
	}

	var footerAppendButtonCell = parentDocument.getElementById(computeId("FOOTER_APPEND_BUTTON_CELL", name, path));
	if (totalCount <= currentCount)
	{
		footerAppendButtonCell.innerText =  "All Shown";
	}

	var tableParent = parentDocument.getElementById(computeId(TABLE_CONTENT_ID, name, path));
	updateExpandCollapseImages(tableParent);
}

function updateFooterInPrevNextMode(name, path, totalCount, currentCount, startIndex, endIndex)
{
	var parentDocument = window.parent.document;

	var footerRow = parentDocument.getElementById(computeId("FOOTER_ROW", name, path));

	footerRow.c_dispcount = currentCount;

	var footerInfoCell = parentDocument.getElementById(computeId("FOOTER_INFO_CELL", name, path));
	if (totalCount <= currentCount)
	{
		footerInfoCell.innerText =  "Displaying All";
	}
	else
	{
		footerInfoCell.innerText =  startIndex + " to " + endIndex + " (" + totalCount + ")";
	}

	//var footerNextButtonCell = parentDocument.getElementById(computeId("FOOTER_NEXT_BUTTON_CELL", name, path));
	if (endIndex >= totalCount)
	{
		hideNavigationButton(parentDocument, name, path, "NEXT")
		//footerNextButtonCell.c_disable = "true";
	}
	else
	{
		showNavigationButton(parentDocument, name, path, "NEXT")
		//footerNextButtonCell.c_disable = "false";
	}

	//var footerPrevButtonCell = parentDocument.getElementById(computeId("FOOTER_PREV_BUTTON_CELL", name, path));
	if (startIndex == 1)
	{
		//footerPrevButtonCell.c_disable = "true";
		hideNavigationButton(parentDocument, name, path, "PREV")
	}
	else
	{
		//footerPrevButtonCell.c_disable = "false";
		showNavigationButton(parentDocument, name, path, "PREV")
	}


	var tableParent = parentDocument.getElementById(computeId(TABLE_CONTENT_ID, name, path));
	updateExpandCollapseImages(tableParent);
}

function hideNavigationButton(parentDocument, name, path, navKey)
{
	parentDocument.getElementById(computeId("FOOTER_" + navKey + "_BUTTON_CELL", name, path)).style.visibility = "hidden";
	parentDocument.getElementById(computeId("FOOTER_LEFT_TD_" + navKey, name, path)).style.visibility = "hidden";
	parentDocument.getElementById(computeId("FOOTER_RIGHT_TD_" + navKey, name, path)).style.visibility = "hidden";
}

function showNavigationButton(parentDocument, name, path, navKey)
{
	parentDocument.getElementById(computeId("FOOTER_" + navKey + "_BUTTON_CELL", name, path)).style.visibility = "visible";
	parentDocument.getElementById(computeId("FOOTER_LEFT_TD_" + navKey, name, path)).style.visibility = "visible";
	parentDocument.getElementById(computeId("FOOTER_RIGHT_TD_" + navKey, name, path)).style.visibility = "visible";
}

function removeRow(name, path, rowIndex)
{
	var str = "name: " + name + " path: " + path + " rowIndex: " + rowIndex;
	var row = getRow(name, path , rowIndex);
	var table = getTreeTable(name,path);

	if (table != undefined && row != undefined)
	{
		table.tBodies[0].deleteRow(row.sectionRowIndex);
	}
	//Check if this row was selected before, if yes then remove it from the selection list
	var selectionObject = getSelectionObject(name, path, rowIndex, -1);
	if (selectionObject.lastRowIndex == rowIndex)
	{
		selectionObject.lastRowIndex = -1;
	}
}



//Popup Menu related changes
function getRowDataPop(_window , name,path, rowIndex)
{
	var row = getRowPop(_window , name,path, rowIndex);
	return getRowData2(name, path, row);
}

function getRowPop(_window , name, path , rowIndex)
{
	return _window.document.getElementById(getRowId(name, path, rowIndex));

}

function getCellDataPop(_window, name,path, rowIndex, colIndex)
{
	var row = getRowPop(_window, name,path, rowIndex);
	if (isTableExpandCollapseCellPop(_window, name, path))
	{
		return row.cells[COLUMN_OFFSET + colIndex].c_cellvalue;
	}
	else
	{
		return row.cells[COLUMN_OFFSET + colIndex - 1].c_cellvalue;
	}
}

function isTableExpandCollapseCellPop(_window, name, path)
{
	return getTreeTablePop(_window, name, path).c_expandCollapseCell == "true";
}

function getTreeTablePop(_window, name,path)
{
	return 	_window.document.getElementById(computeId(TABLE_CONTENT_ID, name, path));
}

function getRowAttributePop(_window, name,path, rowIndex, attribute)
{
	var row = getRowPop(_window, name,path, rowIndex);
	return eval("row.c_" + attribute);
}

function removeRowPop(_window, name, path, rowIndex)
{
	var str = "name: " + name + " path: " + path + " rowIndex: " + rowIndex;
	var row = getRowPop(_window, name, path , rowIndex);
	var table = getTreeTablePop( _window, name,path);

	if (table != undefined && row != undefined)
	{
		table.tBodies[0].deleteRow(row.sectionRowIndex);
	}
	//Check if this row was selected before, if yes then remove it from the selection list
	var selectionObject = getSelectionObjectPop(_window, name, path, rowIndex, -1);
	if (selectionObject.lastRowIndex == rowIndex)
	{
		selectionObject.lastRowIndex = -1;
	}
}

function getSelectionObjectPop( _window, name, path, rowIndex, colIndex)
{
	var frmTable = _window.document.getElementById(computeId3(FRAME_CONTENT_ID, name));
	try
	{
		var testField = frmTable.c_selectionObject.name;
		frmTable.c_selectionObject.name = name;
		frmTable.c_selectionObject.path = path;
		frmTable.c_selectionObject.rowIndex = rowIndex;
		frmTable.c_selectionObject.colIndex = colIndex;
		return frmTable.c_selectionObject;
	}
	catch(e)
	{
		frmTable.c_selectionObject = new SelectionObject(name, path, rowIndex, colIndex);
		return frmTable.c_selectionObject;
	}
}

function refreshTablePop(_window, name, path, url)
{
	if (path == "0")
	{
		postRequest(url, _window.name);//_window.location.href = url;
		return;
	}
	var tableContainerCell = _window.document.getElementById(computeId(NESTED_TD_ID_PREFIX, name, path));
	var rowContainerCell = tableContainerCell.parentElement;
	var expanded = rowContainerCell.c_expanded;
	var loaded = rowContainerCell.c_loaded;
	/*if (loaded == "true" && expanded != "true")
	{
		rowContainerCell.c_loaded = "false";
	}*/
	var frameId = computeId3(FRAME_CONTENT_ID, name);
	postRequest(url + "&loaded="+loaded+"&expanded="+expanded);
	//_window.document.frames[frameId].location.href = url + "&loaded="+loaded+"&expanded="+expanded;
	loadStartPop(_window);
}

function loadStartPop(_window)
{
	currentState = STATE_LOADING;
	_window.status = "System is loading children. Please wait...";
	setWaitCursor(_window.document.body);
}

function fireGroupByRequestPop(_window, name, path, parentPath, rowPath, tableExpandUrl, groupByURL, groupByFieldName, groupByFieldParam)
{
	var url = addGroupByPop(_window, name, path, groupByURL, groupByFieldName, groupByFieldParam, tableExpandUrl, parentPath, rowPath);
	if (path != "0")
	{
		try
		{
			markExpandedPop(_window, NESTED_ROW_ID_PREFIX, name, parentPath, rowPath);
		}
		catch(e)
		{
		}
	}
	fireRequestPop(_window, name, path, url, true);
}

function addGroupByPop(_window, name, path, groupByURL, groupByFieldText, groupByFieldParam, tableExpandUrl, parentPath, rowPath)
{
	var table = getTreeTablePop(_window, name, path);
	table.c_groupByUrl = groupByURL;
	table.c_tableExpandUrl = tableExpandUrl;

	addGroupByCellPop(_window, name, path, groupByFieldText, groupByFieldParam, parentPath, rowPath);

	addToTableGroupByList(table, groupByFieldText);

	var url = getGroupByUrlPop(_window, name, path);//groupByURL + "&" + groupByFieldParam;

	var imgId = "tableTree_TABLE_HEADER_GROUP_BY_IMG*" + name + "_" + path;
	var img = _window.document.getElementById(imgId);
	img.src = img.c_disabled_src;
	img.c_enabled = "false";
	
	return url;

}

function addGroupByCellPop(_window, name, path, groupByFieldText, groupByFieldParam, parentPath, rowPath)
{
	var table = getTreeTablePop(_window, name, path);
	var row = _window.document.getElementById(computeId(INNER_HEADER_ROW_ID_PREFIX, name, path));

	var imagePath = table.c_imagePath;
	var leftImageSrc = "groupbyfieldleft_up.gif";
	var rightImageSrc = "groupbyfieldright_up.gif";


	var endCell = _window.document.getElementById(computeId(GROUP_BY_END_ID_PREFIX, name, path));

	var startIndex = endCell.cellIndex;
	var leftCell = row.insertCell(startIndex);
	leftCell.innerHTML = createLeftImageTagHTML(imagePath, leftImageSrc, 9	);
	leftCell.id = computeId2("GROUP_BY", name, path, groupByFieldText + "0");


	startIndex++;
	//Get the field title from the groupByFieldParam and set the innerhtml 
	var fieldTitleArray = groupByFieldParam.split("&");
	var fieldTitle = fieldTitleArray[1].split("=");
	var titleCell = row.insertCell(startIndex);
	titleCell.className = "tbtr_groupByFieldText";
	//titleCell.innerHTML = groupByFieldText;
	titleCell.innerHTML = fieldTitle[1];
	titleCell.id = computeId2("GROUP_BY", name, path, groupByFieldText + "1");
	titleCell.c_groupByField = groupByFieldText;
	titleCell.c_groupByParam = groupByFieldParam;

	startIndex++;
	var removeHandler = "return removeGroupBy('" + name + "', '" + path + "', '" + groupByFieldText  + "', '" + parentPath  + "', '" + rowPath + "');";
	var rightCell = row.insertCell(startIndex);
	rightCell.innerHTML = createRightImageTagHTML(imagePath, rightImageSrc, 16, removeHandler);
	rightCell.id = computeId2("GROUP_BY", name, path, groupByFieldText + "2");
	rightCell.style.cursor = "hand";

	startIndex++;
	var spacerCell = row.insertCell(startIndex);
	spacerCell.className = "tbtr_groupByTextTD";
	spacerCell.style.width = "5px";
	spacerCell.innerHTML = "&nbsp;";
	spacerCell.id = computeId2("GROUP_BY", name, path, groupByFieldText + "3");
}

function getGroupByUrlPop(_window, name, path)
{
	var table = getTreeTablePop(_window, name, path);
	if (table.c_groupByList == undefined)
	{
		return table.c_tableExpandUrl;
	}
	if (table.c_groupByList.length == 0)
	{
		return table.c_tableExpandUrl;
	}

	var groupByURL = table.c_groupByUrl;

	for (var index=0; index<table.c_groupByList.length; index++)
	{
		var titleCell = _window.document.getElementById(computeId2("GROUP_BY", name, path, table.c_groupByList[index] + 1));
		if (index > 0)
		{
			var params = titleCell.c_groupByParam.split("&", 2);
			var parts = params[0].split("=");
			groupByURL += "&" + parts[0] + PATH_SEPARATOR + (index - 1) + "=" + parts[1];
			if (params[1] != undefined)
			{
			 	groupByURL +=  "&" + params[1];
			 }
		}
		else
		{
			groupByURL += "&" + titleCell.c_groupByParam;
		}
	}
	return groupByURL;
}

function markExpandedPop(_window, rowPrefix, name, tablePath, rowPath)
{
	var row = _window.document.getElementById(computeId2(rowPrefix, name, tablePath, rowPath));
	var image = _window.document.getElementById(computeId2(EX_COL_IMG_PREFIX, name, tablePath, rowPath));
	var linkNode = _window.document.getElementById(computeId2(EX_COL_LINK_PREFIX, name, tablePath, rowPath));

	row.c_loaded = "true";
	row.c_expanded = "true";
	image.src = image.c_OpenImage;
	linkNode.href = "javascript://";
}

function fireRequestPop(_window, name, path, url, targetHiddenFrame)
{
	if (!targetHiddenFrame)
	{
		postRequest(url, _window.name);//_window.location.href = url;
		return;
	}
	postRequest(url);
	//var frameId = computeId3(FRAME_CONTENT_ID, name);
	//_window.document.frames[frameId].location.href = url;
	loadStartPop(_window);
}

function tbtr_expandAll(name, path)
{
	var table = getTreeTable(name, path);
	fireRequest(name, path, table.c_expandallurl, true);
}