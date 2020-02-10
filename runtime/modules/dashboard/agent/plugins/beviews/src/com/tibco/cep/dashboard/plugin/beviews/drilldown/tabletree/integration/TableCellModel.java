package com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.integration;

/**
 * This provides interface between the TableCell and its users via the TableModel#getCellValuAt for rendering different information to TableCell.
 */
public interface TableCellModel {
	/**
	 * Returns the actual Value of the cell.
	 * 
	 * @return
	 */
	public Object getActualValue();

	/**
	 * Returns the tooltip for the cell.
	 * 
	 * @return
	 */
	public String getTooltip();

	/**
	 * Returns the display value used for rendering the cell.
	 * 
	 * @return
	 */
	public Object getDisplayValue();

	/**
	 * Returns the drillable link to drilldown on cell HREF, if return value is not null then it is rendered as HREF.
	 * 
	 * @return
	 */
	public String getDrillableLink();

	/**
	 * Returns the export value used for exporting table content.
	 * 
	 * @return
	 */
	public String getExportValue();

}
