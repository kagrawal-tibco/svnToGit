package com.tibco.cep.dashboard.plugin.beviews.drilldown.visengine;

import com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.integration.TableCellModel;
import com.tibco.cep.dashboard.psvr.ogl.model.ColumnConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.DataColumn;

/**
 * @author rajesh
 *
 */
public class ClassifierHeaderModel implements TableCellModel {

	private ColumnConfig columnConfig;
	private DataColumn dataColumn;

	/**
     *
     */
	public ClassifierHeaderModel(ColumnConfig columnConfig, DataColumn dataColumn) {
		this.columnConfig = columnConfig;
		this.dataColumn = dataColumn;
	}

	public Object getActualValue() {
		return dataColumn.getValue();
	}

	public String getTooltip() {
		return dataColumn.getTooltip();
	}

	public Object getDisplayValue() {
		return dataColumn.getDisplayValue();
	}

	public String getDrillableLink() {
		return dataColumn.getLink();
	}

	public ColumnConfig getColumnConfig() {
		return columnConfig;
	}

	public DataColumn getDataColumn() {
		return dataColumn;
	}

	public String getExportValue() {
		return String.valueOf(getDisplayValue());
	}
}
