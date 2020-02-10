package com.tibco.cep.webstudio.client.decisiontable.model;

import java.util.List;

import com.smartgwt.client.widgets.grid.ListGridField;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.i18n.DisplayUtils;

/**
 * 
 * @author sasahoo
 *
 */
public class TableColumnField extends ListGridField {
	
	private TableColumn column; 
	
	public TableColumnField (TableColumn column, int width, List<ArgumentData> arguments) {
	  	this.column = column;
	  	
	  	setName(column.getName());
		String displayText;
		if(WebStudio.get().getUserPreference().isShowColumnAliasIfPresent() && column.getAlias() != null){
			if(!column.getAlias().equalsIgnoreCase("")){
				displayText = column.getAlias();	
			}else{
				displayText = DisplayUtils.getDTColumnDisplayText(column.getPropertyPath(), column.getName(), arguments);
			}

		}else{
			displayText = DisplayUtils.getDTColumnDisplayText(column.getPropertyPath(), column.getName(), arguments);
		}
	  	
	  	setTitle(displayText);
	  	setWidth(width);
	}

	public TableColumn getColumn() {
		return column;
	}

}
