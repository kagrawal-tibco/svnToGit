package com.tibco.cep.webstudio.client.data.fields;

import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.widgets.grid.CellFormatter;

/**
 * This class is an extension to {@link com.smartgwt.client.data.fields.DataSourceTextField DataSourceTextField}. <br/>
 * Adds the facility of cell formatting to DataSourceTextField.
 * 
 * @author moshaikh
 * 
 */
public class CustomDataSourceTextField extends DataSourceTextField {

	public CustomDataSourceTextField(String name, String title) {
		super(name, title);
	}

	public native void setCellFormatter(CellFormatter formatter) /*-{
		var self = this.@com.smartgwt.client.core.DataClass::getJsObj()();
		self.formatCellValue = $debox($entry(function(value, record, rowNum,
				colNum) {
			var recordJ = @com.smartgwt.client.widgets.grid.ListGridRecord::getOrCreateRef(Lcom/google/gwt/core/client/JavaScriptObject;)(record);
			var valueJ = $wnd.SmartGWT.convertToJavaType(value);
			return formatter.@com.smartgwt.client.widgets.grid.CellFormatter::format(Ljava/lang/Object;Lcom/smartgwt/client/widgets/grid/ListGridRecord;II)(valueJ, recordJ, rowNum, colNum);
		}));
	}-*/;
}
