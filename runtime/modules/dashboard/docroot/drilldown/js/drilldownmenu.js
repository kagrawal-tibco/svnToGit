var arr_params = new Array();

function showRowMenu(menuId, tableName, tablePath, rowIndex, rowParam)
{
	//alert('showRowMenu('+menuId+', '+tableName+', '+tablePath+', '+rowIndex+', '+rowParam+')');
	arr_params = new Array();
	arr_params[0] = menuId;
	arr_params[1] = tableName;
	arr_params[2] = tablePath;
	arr_params[3] = rowIndex;
	arr_params[4] = rowParam;
	showMenu(menuId);
}

function showDrilldownRowMenu(menuId, menuType, rowParam, removeParam, tableName, tablePath, rowIndex)
{
	//alert('showDrilldownRowMenu('+menuId+', '+menuType+', '+rowParam+', '+removeParam+', '+tableName+', '+tablePath+', '+rowIndex+')');
	arr_params = new Array();
	arr_params[0] = menuId;
	arr_params[1] = menuType;
	arr_params[2] = rowParam;
	arr_params[3] = tableName;
	arr_params[4] = tablePath;
	arr_params[5] = rowIndex;
	arr_params[6] = removeParam;
	showMenu(menuId);
}

function showColumnMenu(menuId, tableName, tablePath, colIndex, colParam)
{
	arr_params = new Array();
	arr_params[0] = menuId;
	arr_params[1] = tableName;
	arr_params[2] = tablePath;
	arr_params[3] = colIndex;
	arr_params[4] = colParam;
	showMenu(menuId);
}

function showHeaderMenu(menuId, menuType, hdrParam, refreshParam, tableName, tablePath)
{
    //alert("showHeaderMenu("+menuId+", "+menuType+", "+hdrParam+", "+refreshParam+", "+tableName+", "+tablePath+")");
	arr_params = new Array();
	arr_params[0] = menuId;
	arr_params[1] = menuType;
	arr_params[2] = hdrParam;
	arr_params[3] = refreshParam;
	arr_params[4] = tableName;
	arr_params[5] = tablePath;
	showMenu(menuId);
}

function showHeaderMenu2(menuId, tableName, tablePath, hdrParam)
{
	arr_params = new Array();
	arr_params[0] = menuId;
	arr_params[1] = tableName;
	arr_params[2] = tablePath;
	arr_params[3] = hdrParam;
	showMenu(menuId);
}

function showGroupByMenu(menuId, menuType, groupByUrl, tableName, tablePath, parentTablePath, rowPath, tableExpandUrl)
{
	arr_params = new Array();
	arr_params[0] = menuId;
	arr_params[1] = menuType;
	arr_params[2] = groupByUrl;
	arr_params[3] = tableName;
	arr_params[4] = tablePath;
	arr_params[5] = parentTablePath;
	arr_params[6] = rowPath;
	arr_params[7] = tableExpandUrl;
	
	var idParts = menuId.split("*");
	var imgGroupBy = document.getElementById("tableTree_TABLE_HEADER_GROUP_BY_IMG*" + idParts[1]);
	if (imgGroupBy.c_enabled == "true")
	{
		showMenu(menuId);
	}
	else
	{
		//alert("menu is disabled");
	}
}

function showRemoveEventWindow(_window ){
	hideMenu();
	_window.top.document.body.style.cursor = "wait";
	var retVal = _window.showModalDialog(arr_params[6],"","dialogHeight:250px; dialogWidth:450px; help: No; resizable: Yes; status: No; scroll: No;");
	if (retVal != undefined && retVal.length != 0){
		var isEventDeleted = _window.showModalDialog(retVal,"","dialogHeight:250px; dialogWidth:450px; help: No; resizable: Yes; status: No; scroll: No;");
		if (isEventDeleted != undefined && isEventDeleted.length != 0){
			if (isEventDeleted == "true"){
					removeRowPop(_window ,arr_params[3],arr_params[4],arr_params[5]);
			}
		}
	}
	_window.top.document.body.style.cursor = "auto";
}


/********************
* This is where we are creating the URL for the query frame. 
* We are doing it in 2 ways - 
* - by setting it in the Iframe SRC
* - by calling 	window.top.frames["queryholder"].setQueryValues(typeId,colHeaderArray,rowValueArray);
********************/

function rowMenuClicked( _window , menuItemId, menuItemParam)
{
	//arr_params[1], arr_params[2], arr_params[3] is name path and rowindex in that order. 
	//debugger;
	if (menuItemId == "ett")
	{
		showETTWindow(_window, menuItemId, menuItemParam);
	}
	else if(menuItemId == "srcevent" || menuItemId == "allevent")
	{
		showCorrelatedWindow(_window, menuItemId, menuItemParam);
	}
	else if(menuItemId == "showAssets")
	{
		indexOfAmpersand = arr_params[2].indexOf("?");
		queryString = arr_params[2].substring(indexOfAmpersand+1);
		var href = contextpath+"/pagelet/asset/showrelatedassets.do?" + queryString;
		_window.top.document.all.pan_1_1_holder.src = href;
	}
	else if(menuItemId == "remove")
	{
		showRemoveEventWindow();
	}
	else if(menuItemId == "allfields")
	{
		var data = getRowDataPop( _window , arr_params[3], arr_params[4], arr_params[5]);
		var queryParams = menuItemParam;
		for(var queryParamIndex=0; queryParamIndex<data.length; queryParamIndex++)
		{
			var key = "{"+ queryParamIndex + "}";
			queryParams = queryParams.replace(key,encodeURIComponent(data[queryParamIndex]));
		}

		var href = arr_params[2] + "&" + queryParams;
		updateQueryFrame(_window , href);
	}
	else 
	{
		//debugger;
		var colIndex = COLUMN_OFFSET + parseInt(menuItemId);
		var fieldVal = getCellDataPop(_window, arr_params[3], arr_params[4], arr_params[5], parseInt(menuItemId) );
		var key = "{"+ menuItemId + "}";
		var queryParam = "fld_" + menuItemParam.replace(key,encodeURIComponent(fieldVal));
		var href = arr_params[2] + "&" + queryParam;
		updateQueryFrame(_window, href);
	}
}

function columnMenuClicked(menuItemId, menuItemParam)
{
}

/********************
* This is where we are creating the URL for the query frame. 
* We are doing it in 2 ways - 
* - by setting it in the Iframe SRC
* - by calling 	window.top.frames["queryholder"].setQueryValues(typeId,null,null);
* - we are sending null as the last 2 params as this is the header menu and doesnt need row and columns
********************/
function headerMenuClicked(_window, menuItemId, menuItemParam)
{
	if (menuItemId == "refreshtable")
	{
        //MODIFIED by Anand to fix BE-11144 on 02/10/2011
        refreshTablePop(_window, arr_params[3], arr_params[4], arr_params[2]);
	}
	else
	{
		_window.top.document.all.pan_0_0_holder.src = arr_params[2];
	}
}

function menuItemClicked(_window, menuRow, parentMenuId, rootMenuId, menuItemId, menuItemParam)
{
	if (!isMenuEnabled(_window, arr_params[0], menuItemId))
	{
		return;
	}
	if (arr_params[1] == "headermenu")
	{
		headerMenuClicked(_window, menuItemId, menuItemParam);
	}
	else if (arr_params[1] == "rowmenu")
	{
		rowMenuClicked(_window, menuItemId, menuItemParam);
	}
	else if (arr_params[1] == "groupbymenu")
	{
		groupByMenuClickedPop(_window, menuItemId, menuItemParam);
	}
	else if (arr_params[1] == "ettFilterCatgory")
	{
		//categoryMenuItemClicked(_window, menuItemId, menuItemParam);
	}
	else
	{
		//rowMenuClicked(_window, menuItemId, menuItemParam);
	}
	hideMenu();
}
//Displays the ett Window.
function showETTWindow(_window, menuItemId, menuItemParam)
{
	var sUrl = menuItemParam + "&"
	sUrl += "typeid=" + getRowAttValuePop(_window,"typeid");
	sUrl += "&instanceid=" + getRowAttValuePop(_window,"instanceid");
	
	var w = 480, h = 340;

	if (_window.document.all || _window.document.layers) {

	   w = screen.availWidth;
	   h = screen.availHeight;
	}

	var popW = 850, popH = 400;

	var leftPos = (w-popW)/2, topPos = (h-popH)/2;
	var ettWindow = _window.open(sUrl, "ett_" + pageletsessionid, "top=" + topPos + ", left=" + leftPos + ",height=400,width=850,location=no,menubar=no,resizable=yes,scrollbars=no,status=no,titlebar=yes,toolbar=no");
	newWindowOpened(ettWindow);
}

function showCorrelatedWindow(_window, menuItemId, menuItemParam)
{
	//alert('showCorrelatedWindow('+menuItemId+','+menuItemParam+')');
	var sUrl = menuItemParam + "&"
	sUrl += "typeid=" + getRowAttValuePop(_window,"typeid");
	sUrl += "&instanceid=" + getRowAttValuePop(_window,"instanceid");
	
	var w = 480, h = 340;

	if (_window.document.all || _window.document.layers) {

	   w = screen.availWidth;
	   h = screen.availHeight;
	}

	var urlParamsParser = new URLParametersParser(menuItemParam,true);
	
	var popW = 850, popH = 400;
	
	var leftPos = (w-popW)/2, topPos = (h-popH)/2;
	var ettWindow = _window.open(sUrl, menuItemId + "_" + urlParamsParser.getValue("sessionid"), "top=" + topPos + ", left=" + leftPos + ",height=400,width=850,location=no,menubar=no,resizable=yes,scrollbars=no,status=no,titlebar=yes,toolbar=no");
	newWindowOpened(ettWindow);
}

function getRowAttValuePop(_window, attribute)
{
	return getRowAttributePop(_window, arr_params[3], arr_params[4], arr_params[5], attribute);
} 

function updateQueryFrame(_window ,href)
{
	try
	{
		_window.top.document.all.pan_0_0_holder.src = href;
	}
	catch(e)
	{
		alert(e);
		//_window.opener.top.document.all.pan_0_0_holder.src = href;
	}	
}

function getCellDataETT(name, path, rowIndex, colIndex)
{
	var row = getRow(name, path, rowIndex);
	var cell = row.cells[COLUMN_OFFSET + 1];
	var contentTable = cell.getElementsByTagName("table")[0];
	return contentTable.rows[0].cells[colIndex].innerText;
}

function getRowDataEtt(name, path, rowIndex)
{
	var row = getRow(name, path, rowIndex);
	var cell = row.cells[COLUMN_OFFSET + 1];
	var contentTable = cell.getElementsByTagName("table")[0];
	var contentCells = contentTable.rows[0].cells;
	var rowData = new Array();
	for (var colIndex =0; colIndex < contentCells.length; colIndex++)
	{
		rowData[colIndex] = contentCells[colIndex].innerText;
	}
	return rowData;
}

function encodeURIComponent(val)
{
	return val;
}

function groupByMenuClicked(menuItemId, menuItemParam)
{
	fireGroupByRequest(arr_params[3], arr_params[4], arr_params[5],  arr_params[6], arr_params[7], arr_params[2], menuItemId,  menuItemParam);
	disableMenuItem(window,arr_params[0], menuItemId);
	//addGroupBy();
}

// for pop up menu 

function groupByMenuClickedPop(_window,menuItemId, menuItemParam)
{
	
	fireGroupByRequestPop(_window, arr_params[3], arr_params[4], arr_params[5],  arr_params[6], arr_params[7], arr_params[2], menuItemId,  menuItemParam);
	disableMenuItem(_window, arr_params[0], menuItemId);
	//addGroupBy();
}
