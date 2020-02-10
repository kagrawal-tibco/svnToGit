package com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.integration;

public class DefaultTableCellModel implements TableCellModel {

	private Object actualValue;
	private String tooltip;
	private Object displayValue;
	private String drillableLink;

	/**
	 * Constructor for String Cells, where actual, display and tooltip are same value.
	 * 
	 * @param value
	 *            Used for actual, display and tooltip.
	 */
	public DefaultTableCellModel(Object value) {
		actualValue = value;
		displayValue = value;
		tooltip = value.toString();
	}

	/**
	 * Constructor for String Cells, where actual and display same value.
	 * 
	 * @param value
	 *            Used for actual and display values.
	 * @param tooltip
	 *            Used for tooltip display.
	 */
	public DefaultTableCellModel(Object value, String tooltip) {
		actualValue = value;
		displayValue = value;
		this.tooltip = tooltip;
	}

	/**
	 * Constructor for String Cells, where actual, display and tooltip values are different.
	 * 
	 * @param actualValue
	 *            Used for actual and display values.
	 * @param displayValue
	 *            Used for display and rendering purpose.
	 * @param tooltip
	 *            Used for tooltip display.
	 */
	public DefaultTableCellModel(Object actualValue, Object displayValue, String tooltip) {
		this.actualValue = actualValue;
		this.displayValue = displayValue;
		this.tooltip = tooltip;
	}

	/**
	 * Constructor for String Cells, where actual, display and tooltip values are different.
	 * 
	 * @param actualValue
	 *            Used for actual and display values.
	 * @param displayValue
	 *            Used for display and rendering purpose.
	 * @param tooltip
	 *            Used for tooltip display.
	 * @param drillableLink
	 *            Link to drilldown when click on this cell
	 */
	public DefaultTableCellModel(Object actualValue, Object displayValue, String tooltip, String drillableLink) {
		this.actualValue = actualValue;
		this.displayValue = displayValue;
		this.tooltip = tooltip;
		this.drillableLink = drillableLink;
	}

	public Object getActualValue() {
		return actualValue;
	}

	public String getTooltip() {
		return tooltip;
	}

	public Object getDisplayValue() {
		return displayValue;
	}

	@Override
	public String toString() {
		return displayValue.toString();
	}

	public String getDrillableLink() {
		return drillableLink;
	}

	public String getExportValue() {
		return String.valueOf(actualValue);
	}

}