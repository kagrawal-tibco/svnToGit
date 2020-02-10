package com.tibco.cep.studio.dashboard.ui.chartcomponent.types.table;

import java.util.List;

import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.model.LocalUnifiedComponent;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.model.LocalUnifiedSeriesConfig;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.model.LocalUnifiedVisualization;

public class TableColumnSizer {

	private LocalElement component;
	private LocalElement visualization;
	private String[] seriesConfigWidthPropertyNames;

	private boolean multiModeOn;

	public TableColumnSizer(LocalElement component) throws Exception {
		super();
		this.component = component;
		this.visualization = component.getElement(LocalUnifiedVisualization.TYPE);
		List<LocalElement> seriesConfigs = visualization.getChildren(LocalUnifiedSeriesConfig.TYPE);
		seriesConfigWidthPropertyNames = new String[seriesConfigs.size()];
		int i = 0;
		for (LocalElement seriesConfig : seriesConfigs) {
			boolean hasIndicatorColumn = !seriesConfig.isPropertyValueSameAsDefault("IndicatorValueColumnField");
			if (hasIndicatorColumn == true) {
				seriesConfigWidthPropertyNames[i] = "IndicatorValueColumnFieldWidth";
			}
			else {
				seriesConfigWidthPropertyNames[i] = "TextValueColumnFieldWidth";
			}
			i++;
		}
		multiModeOn = "MultiMeasure".equalsIgnoreCase(this.component.getPropertyValue(LocalUnifiedComponent.PROP_KEY_SUBTYPES));
	}

	public int[] getWidths() throws Exception {
		List<LocalElement> seriesConfigs = visualization.getChildren(LocalUnifiedSeriesConfig.TYPE);
		if (seriesConfigs.size() != seriesConfigWidthPropertyNames.length) {
			throw new IllegalStateException(component+"'s series configuration is inconsistent with the recorded configuration");
		}
		int[] widths = new int[multiModeOn == true ? 2 : seriesConfigs.size() + 1];
		int i = 0;
		// category column width
		widths[i] = parseInt(visualization.getPropertyValue("CategoryColumnWidthPercentage"));
		i++;
		for (LocalElement seriesConfig : seriesConfigs) {
			int width = parseInt(seriesConfig.getPropertyValue(seriesConfigWidthPropertyNames[i-1]));
			if (multiModeOn == true) {
				if (i == 1) {
					widths[i] = width;	
				}
				else {
					if (widths[1] != width) {
						throw new IllegalStateException("Widths are not equals across all series configs for a multi measure table component");
					}
				}
			} else {
				widths[i] = width;
			}
			i++;
		}
		return widths;
	}

	public void updateWidths() throws Exception {
		List<LocalElement> seriesConfigs = visualization.getChildren(LocalUnifiedSeriesConfig.TYPE);
		if (seriesConfigs.size() != seriesConfigWidthPropertyNames.length) {
			throw new IllegalStateException(component+"'s series configuration is inconsistent with the recorded configuration");
		}
		int[] widths = new int[seriesConfigWidthPropertyNames.length+1];
		widths[0] = parseInt(visualization.getPropertyValue("CategoryColumnWidthPercentage"));
		for (int i = 1; i < widths.length; i++) {
			widths[i] = parseInt(seriesConfigs.get(i-1).getPropertyValue(seriesConfigWidthPropertyNames[i-1]));
			
		}
		if (multiModeOn == false) {
			int completeWidth = 0;
			for (int i = 0; i < widths.length; i++) {
				completeWidth = completeWidth + widths[i];
			}
			// recompute widths if needed
			if (completeWidth > 100) {
				int numCols = seriesConfigs.size() + 1;
				// we need to recompute the widths
				// we force all widths to be same, letting round offs go to category
				// column
				int baseWidth = 100 / numCols;
				int remainingWidth = 100;
				for (int i = 1; i < numCols; i++) {
					seriesConfigs.get(i - 1).setPropertyValue(seriesConfigWidthPropertyNames[i - 1], baseWidth + "%");
					remainingWidth = remainingWidth - baseWidth;
				}
				visualization.setPropertyValue("CategoryColumnWidthPercentage", remainingWidth + "%");
			}
		}
		else {
			//check if first two widths are OK
			int categoryColWidth = widths[0];
			int valueColWidth = widths[1];
			if (categoryColWidth + valueColWidth != 100) {
				//we have to update the widths
				visualization.setPropertyValue("CategoryColumnWidthPercentage", "50%");
				int i = 0;
				for (LocalElement seriesConfig : seriesConfigs) {
					seriesConfig.setPropertyValue(seriesConfigWidthPropertyNames[i], "50%");
					i++;
				}
			}
			else {
				//we have first two widths as OK, check if remaining widths are same
				for (int i = 2; i < widths.length; i++) {
					if (widths[i] != valueColWidth) {
						seriesConfigs.get(i).setPropertyValue(seriesConfigWidthPropertyNames[i], valueColWidth+"%");
					}
				}
			}
		}
	}

	public void updateWidth(int index, int width) throws Exception {
		LocalElement visualization = component.getElement(LocalUnifiedVisualization.TYPE);
		if (index == 0) {
			visualization.setPropertyValue("CategoryColumnWidthPercentage", width + "%");
		} else {
			List<LocalElement> seriesConfigs = visualization.getChildren(LocalUnifiedSeriesConfig.TYPE);
			if (multiModeOn == false) {
				seriesConfigs.get(index - 1).setPropertyValue(seriesConfigWidthPropertyNames[index - 1], width+"%");
			}
			else  {
				if (index > 1) {
					throw new IllegalArgumentException("Invalid series config index for a multi measure table component");
				}
				int i = 0;
				for (LocalElement seriesConfig : seriesConfigs) {
					seriesConfig.setPropertyValue(seriesConfigWidthPropertyNames[i], width+"%");
				}
			}
		}
	}

	public void updateWidths(int[] widths) throws Exception {
		LocalElement visualization = component.getElement(LocalUnifiedVisualization.TYPE);
		List<LocalElement> seriesConfigs = visualization.getChildren(LocalUnifiedSeriesConfig.TYPE);
		visualization.setPropertyValue("CategoryColumnWidthPercentage", widths[0] + "%");
		if (multiModeOn == false) {
			for (int i = 1; i < widths.length; i++) {
				seriesConfigs.get(i - 1).setPropertyValue(seriesConfigWidthPropertyNames[i - 1], widths[i]+"%");
			}
		}
		else {
			if (widths.length > 2) {
				throw new IllegalArgumentException("Invalid widths for a multi measure table component");
			}
			int i = 0;
			for (LocalElement seriesConfig : seriesConfigs) {
				seriesConfig.setPropertyValue(seriesConfigWidthPropertyNames[i], widths[1]+"%");
			}
		}
	}

	private int parseInt(String value) {
		if (value.endsWith("%") == true) {
			value = value.substring(0, value.length() - 1);
		}
		return Integer.parseInt(value);
	}

}
