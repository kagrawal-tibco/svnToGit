var last_Sorted_Column;
var last_Sorted_Image;
var COLUMN_OFFSET = 2;
var startTime; 
var endTime;
var key;

function sortTableByColumn(name, path, colIndex, state, nextState, url)
{
	/*var newState = 1;
	if (state == 0)
	{
		newState = 1;
	} 
	else if (state == 1)
	{
		newState = -1;
	}
	else if (state == -1)
	{
		newState = 1;
	}*/
	refreshTable(name, path, url + "&sortstate=" + nextState);
}


function sortColumn(tableId, tableColumnId, imageId, colIndex)
{
	if (mouseDownTaskMode != TASKMODE_SORT)
	{
		return;
	}
	
	//debug;
	//alert("tableId=" +tableId + "\n tableColumnId=" + tableColumnId + "\n colIndex=" +colIndex);
	//we can calculate the colinded depending upon the table cell elements.

	var table = document.getElementById(tableId);
	var columnHeader = document.getElementById(tableColumnId);
	var sortAscending = true;
	var dataType = "string";
    colIndex  = colIndex + COLUMN_OFFSET;
	startTime = new Date();
	if(!columnHeader || !table)
	{
		return;
	} 
	else
	{
		if(columnHeader.c_sortable)
		{
			var imageSource;
			
			dataType = columnHeader.c_columnDataType;
			//alert("dataType is : " + dataType);
			if(!dataType)
			{
				dataType="string";
			}
			if(columnHeader.c_sortState =="-1")
			{
				//already sorted to asc
				sortAscending = false;
				columnHeader.c_sortState ="1";
				columnHeader.className = columnHeader.c_descendingStyle;
				ARROW = '&nbsp;&nbsp;&darr;';								
				imageSource = columnHeader.c_descendingImage;
			}
			else
			{
				columnHeader.c_sortState ="-1";
				columnHeader.className = columnHeader.c_ascendingStyle;
				ARROW = '&nbsp;&nbsp;&uarr;';	
				imageSource = columnHeader.c_ascendingImage;
			}

			if (last_Sorted_Column)
			{
				if (last_Sorted_Column != columnHeader)
				{
					last_Sorted_Column.c_sortState = "0";
					if (last_Sorted_Column != columnHeader)
					{
						last_Sorted_Column.className = columnHeader.c_unSortedStyle;
						if(last_Sorted_Image)
						{
							last_Sorted_Image.src = last_Sorted_Column.c_unSortedImage;
						}
					}
				}
			}
			last_Sorted_Column = columnHeader;
			setArrow(imageId,imageSource);
			
		}
	}


	var rowIndex; 
	for(rowIndex=0; rowIndex< table.rows.length ; rowIndex++)
	{
		if(table.rows[rowIndex].sortable){
			break;
		}
	}
	//debug;
	//check if sorting is allowed
	rowIndex++; //next rows contains the data to sort
	if(rowIndex< table.rows.length)
	{
		if ( table.rows[rowIndex].c_type == "filter_row")
		{
			rowIndex++; //next rows contains the data to sort
		}
	}
	if(rowIndex< table.rows.length)
	{
		
		sortStartFunction(table, rowIndex,colIndex, sortAscending,dataType);
	}
	
	updateExpandCollapseImages(table);
	
}


function setArrow(elementId, imageSource)
{
	//debug;
	var element = document.getElementById(elementId);
	if (element)
	{
		element.src = imageSource;
		last_Sorted_Image =element;
	}
}

function sortStartFunction(table, startRowIndex, sortColumnIndex, sortAscending,dataType)
{
	//debug;
	//sortColumnIndex += 2;
	
	var rowToSort = new Array();
	var rowToSortIndex=0;
	var table_body = table.tBodies[0];
	var table_body_rows = table_body.rows;
	for(var i = 0 ;i < table_body_rows.length; i++)
	{
		var currentRowIndex = i;
		var row = table_body_rows[i];
		if (row.c_type == "row")
		{
			var groupRows = new Array();
			var groupRowIndex=0;		
			for (var j=i+1;  j < table_body_rows.length; j++)
			{
				if(table_body_rows[j].c_type == "nestedtable_row")
				{
					groupRows[groupRowIndex++] = table_body_rows[j];
				}
				else if (table_body_rows[j].c_type == "row")
				{
					break;
				}
				i++;
			}
			rowToSort[rowToSortIndex] = new Array(1);
			rowToSort[rowToSortIndex][0] = row;
			rowToSort[rowToSortIndex][1] = groupRows;
			rowToSort[rowToSortIndex][2] = currentRowIndex;
			rowToSort[rowToSortIndex][3] = convertDataType(row.cells[sortColumnIndex].innerText,dataType); 
			rowToSortIndex++;
		}
	}		

	//debug;
	key =3;
	rowToSort.sort(customArraySort);
				   
    endTime = new Date();

	startTime = new Date();
	if (sortAscending ==false)
	{
		rowToSort.reverse();
	}
	arrangRows(table,startRowIndex, rowToSort);
		
	

}

function arrangRows(table,startRowIndex, rowToSort)
{
	var table_body = table.tBodies[0];
	var table_body_rows = table_body.rows;
	
	var currentRowIndex = startRowIndex;
	var newRow;
	var groupRows;
	var per_value = 0;
	var max_value = rowToSort.length;
	var sort_status;
	var current_row;
	
	var footerRow = table_body.rows[table_body.rows.length - 1];
	if (footerRow.c_type != "table_footer_row")
	{
		footerRow = null;
	}
	for (var i =0 ; i< max_value; i++)
	{
		current_row = rowToSort[i][0];
		table_body.appendChild(current_row);
		sort_status = " sorting " + parseInt(((100 *  i)/ max_value)) + "% ...";
		window.status = sort_status;
		setAlternateColor(rowToSort[i][0],i); //now set the color accordingly
		
		groupRows = rowToSort[i][1];
		for(var j=0; j < groupRows.length; j++)
		{
			table_body.appendChild(groupRows[j]);
		}
	}
	if (footerRow != null)
	{
		table_body.appendChild(footerRow);
	}
	window.status = "";
}



function setAlternateColor(row, index)
{
	if(index % 2 == 0)
	{
		//if (row.className != "evenRowStyle")
			row.className = "evenRowStyle";
	}
	else
	{
		//if (row.className != "oddRowStyle")
			row.className = "oddRowStyle";
	}
	
}

function convertDataType(datavalue, dataType)
{
	var convertedValue = datavalue;
	//debug; 
	if(dataType == "date")
	{
		convertedValue = convertToDate(datavalue);
	}
	else if (dataType == "number")
	{
		convertedValue = convertToNumber(datavalue);
	}
	if (convertedValue != null)
	{
		return convertedValue;
	}
	return datavalue;
}

function convertToDate(x)
{
	var xDate;
	xDate = new Date(x);
	if (xDate.toString() == 'NaN' || 
		xDate.toString() == 'Invalid Date')
		return null;
	else
		return xDate;
}

function convertToNumber(x)
{
	var xNumber = new Number(x);
	if (isNaN(xNumber) ||
		xNumber.toString() == 'NaN' || 
		xNumber.toString() == 'Invalid Number')
		return null;
	else
		return xNumber;
}



//here we got the 2 objects to be compared
function customArraySort(object1, object2)
{
	if (object1[key]<object2[key])
	{
		return -1;
	}
	else if (object1[key]> object2[key])
	{
		return 1;	
	}		
	return 0;
}

