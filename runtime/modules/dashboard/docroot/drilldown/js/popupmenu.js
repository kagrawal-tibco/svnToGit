function RootPopupMenu(menuId, _window)
{
	this._popupMenu = new PopupMenu(null, null, null, menuId, _window, null, null);
}

function PopupMenu(rootMenuId, parentMenuId, parentMenuItemId, menuId, _window, _parentMenu, _parentMenuItem)
{
	this._menuId = menuId;

	this._parentWindow = _window;
	this._parentDocument = _window.document;
	this._parentMenu = _parentMenu;
	this._parentMenuItem = _parentMenuItem;

	this._popupObj = null;

	this.show = show;
	this.hide = hide;

	this._childPopupMenu = null;
}


function show(x, y)
{
	if (this._popupObj != null)
	{
		this.hide();
	}
	this._popupObj = this._parentWindow.createPopup();
	this._popupDocument = this._popupObj.document;

	
	var element = this._parentDocument.getElementById(this._menuId);
	moveFirstRowToTheBottom(element);
	element.style.zIndex = -9999;
	element.style.position = "absolute";
	element.style.left = -9999;
	element.style.top = -9999;
	element.style.display = "";
	var width = element.offsetWidth;
	var height = element.offsetHeight;
	element.style.display = "none";


	var popupBody = this._popupDocument.body;
	popupBody.innerHTML = element.innerHTML;

	popupBody.style.border = "1px solid black";

	this._popupObj.show(x, y, width, height, this._parentDocument.body);
}

function hide()
{
	if (this._popupObj != null)
	{
		if (this._childPopupMenu != null)
		{
			this._childPopupMenu.hide();
			this._childPopupMenu = null;
		}
		this._popupObj.hide();
		this._popupObj = null;
	}
}



var rootPopupMenu = null;

function showMenu(menuId)
{
	//TBD
//debugger;
	var xPos = window.event.srcElement.scrollLeft + window.event.x + 10;
	var yPos = window.event.srcElement.scrollTop + window.event.y;

	if (rootPopupMenu != null && rootPopupMenu._popupMenu._menuId == menuId)
	{
		//Menu is already visible. Hide it.
		//rootPopupMenu._popupMenu.hide();
	}

	if (rootPopupMenu != null)
	{
		//Hides the previous menu if any visible.
		hideMenu();
	}

	rootPopupMenu = new RootPopupMenu(menuId, window);
	rootPopupMenu._popupMenu.show(xPos, yPos);
}

function hideMenu()
{
	if (rootPopupMenu != null)
	{
		rootPopupMenu._popupMenu.hide();
		rootPopupMenu = null;
	}
}

function showSubMenu(_window, row, rootMenuId, parentMenuId, menuId)
{
	var xPos = row.offsetWidth;
	var yPos = row.offsetHeight - 15;
	if (rootMenuId != parentMenuId)
	{
		yPos = row.offsetParent.offsetHeight - 15;
	}
	var parentMenuItemId = "menuitem_" + menuId;
	var childMenu = new PopupMenu(rootMenuId, parentMenuId, parentMenuItemId, menuId, _window, null, null);
	childMenu.show(xPos, yPos);
	row._popupMenu = childMenu;
	var table = row.parentElement;
	table._popupMenu = childMenu;
	table._row = row;
}

function hideSubMenuNew(row)
{
	var table = row.parentElement;
	if (!row._popupMenu)
	{
		if (table._popupMenu && table._popupMenu != null)
		{
			table._popupMenu.hide();
			table._popupMenu = null;
		}
	}
	else if (row._popupMenu != null && table._popupMenu && table._popupMenu != null && row._popupMenu._menuId != table._popupMenu._menuId)
	{
		table._popupMenu.hide();
		table._popupMenu = null;
		table._row._popupMenu = null
		table._row = null;
	}
}

function hideLastSubMenu(_window)
{
	if (_window.popupMenu != null)
	{
		_window.popupMenu.hide();
		_window.popupMenu = null;
	}
}

function getRowElement(srcElement)
{
	if (srcElement.tagName == "IMG")
	{
		srcElement = srcElement.parentElement;
	}
	if (srcElement.tagName == "TD")
	{
		return srcElement.parentElement;
	}
	return srcElement;
}


function onMouseOver(_window, row)
{
	changeStyle(row, "clsMenuHighlightTD", "clsMenuHighlightTD");
	hideSubMenuNew(row);
}

function onMouseOut(_window, row)
{
	changeStyle(row, "clsMenuNormalTD", "clsMenuImageTD");
}

function changeStyle(row, titleStyleClass, imgStyleClass)
{
	//var row = getRowElement(window.event.srcElement);
	if (!isRowEnabled(row))
	{
		return;
	}
	changeStyle2(row, titleStyleClass, imgStyleClass);
}

function changeStyle2(row, titleStyleClass, imgStyleClass)
{
	if (row.c_subMenuVisible)
		return;
	
	if (row.tagName == "TR")
	{
		var cells = row.cells;
		var cellIndex = 0;
		for (; cellIndex < cells.length; cellIndex++)
		{
	        var tdNode = cells[cellIndex];
			changeElementStyle(tdNode, titleStyleClass, imgStyleClass);
		}
	}
	changeElementStyle(row, titleStyleClass, imgStyleClass);
}

function changeElementStyle(element, titleStyleClass, imgStyleClass)
{
	if (titleStyleClass == "clsMenuHighlightTD")
	{
		element.style.backgroundColor = "#d8d8d8";
		element.style.color = "#000000";
		element.style.cursor = "hand";
	}
	else if (titleStyleClass == "clsMenuDisableTD")
	{
		element.style.backgroundColor = "#d8d8d8";
		element.style.color = "#7f7f7f";
		element.style.cursor = "auto";
	}
	else
	{
		element.style.backgroundColor = "#ffffff";
		element.style.color = "#000000";
		element.style.cursor = "hand";
	}
	//element.className = titleStyleClass;
}

function menuClicked()
{
//	alert("menuClicked");
	//hideMenu();
}

function disableMenuItem(_window, menuId, menuItemId)
{
	try
	{
		var elemId = "menuitem_" + menuId + "_" + menuItemId;
		var row = document.getElementById(elemId);
		changeStyle2(row, "clsMenuDisableTD", "clsMenuDisableTD");
		row.c_enable = false;
	}
	catch(e)
	{
	}
}

function enableMenuItem(menuId, menuItemId)
{
	try
	{
		var elemId = "menuitem_" + menuId + "_" + menuItemId;
		var row = document.getElementById(elemId);
		changeStyle2(row, "clsMenuNormalTD", "clsMenuImageTD");
		row.c_enable = true;
	}
	catch(e)
	{
	}
}

function isMenuEnabled(_window, menuId, menuItemId)
{
	var elemId = "menuitem_" + menuId + "_" + menuItemId;
	var row = _window.document.getElementById(elemId);
	return isRowEnabled(row);
}

function isRowEnabled(row)
{
	if (row == undefined) return true;
	if (row.c_enable == undefined) return true;
	if ("" + row.c_enable == "false")
	{
		row.c_enable = false;
	}
	return row.c_enable;
}


function moveFirstRowToTheBottom(divElem)
{
	var table = divElem.children[0];
	var firstRow = table.rows[0];
	var lastRow = table.rows[table.rows.length-1];
	if (!isMoreMenu(firstRow.id))
	{
		return;
	}
	if (!lastRow.moved)
	{
		firstRow.moved = true;
		table.moveRow(0, table.rows.length - 1);
	}
}

function isMoreMenu(id)
{
	var id2 = id.substr(id.length - "_child_menu_".length, id.length);
	if (id2 == "_child_menu_")
	{
		return true;
	}
	return false;
}
