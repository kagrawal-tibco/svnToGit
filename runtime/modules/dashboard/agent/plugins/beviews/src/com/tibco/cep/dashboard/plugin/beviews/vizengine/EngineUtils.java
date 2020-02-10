package com.tibco.cep.dashboard.plugin.beviews.vizengine;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import com.tibco.cep.kernel.service.logging.Logger;

import com.tibco.cep.dashboard.psvr.mal.ElementNotFoundException;
import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.mal.model.MALCategoryGuidelineConfig;
import com.tibco.cep.dashboard.psvr.mal.model.MALChartComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALTextComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALTextVisualization;
import com.tibco.cep.dashboard.psvr.mal.model.types.SortEnum;
import com.tibco.cep.dashboard.psvr.ogl.DataColumnComparator;
import com.tibco.cep.dashboard.psvr.ogl.model.DataColumn;
import com.tibco.cep.dashboard.psvr.ogl.model.DataRow;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.dashboard.psvr.util.StringComparator;
import com.tibco.cep.dashboard.psvr.vizengine.VisualizationException;

public class EngineUtils {

	/**
	 * @deprecated
	 * @param logger
	 * @param component
	 * @param pCtx
	 * @return
	 * @throws VisualizationException
	 * @throws MALException
	 * @throws ElementNotFoundException
	 */
    static Map<String, Object> getCategoryValuesMapHACK(Logger logger, MALComponent component, PresentationContext pCtx) throws VisualizationException, MALException, ElementNotFoundException {
		MALCategoryGuidelineConfig categoryGuideLineConfig = getCategoryGuideLineConfig(component);
		SortEnum sortEnum = categoryGuideLineConfig == null ? null : categoryGuideLineConfig.getSortOrder();
        Map<String, Object> categoryValuesMap = new LinkedHashMap<String, Object>();
		if (sortEnum != null) {
			switch (sortEnum.getType()) {
			case SortEnum.ASCENDING_TYPE:
				categoryValuesMap = new TreeMap<String, Object>(new StringComparator(true));
				break;
			case SortEnum.DESCENDING_TYPE:
				categoryValuesMap = new TreeMap<String, Object>(new StringComparator(false));
				break;
			}
		}
		return categoryValuesMap;
    }

    /**
     * @deprecated
     * @param logger
     * @param component
     * @param pCtx
     * @return
     * @throws VisualizationException
     * @throws MALException
     * @throws ElementNotFoundException
     */
    static DataColumnComparator getDataColumnComparatorHACK(Logger logger, MALComponent component, PresentationContext pCtx) throws VisualizationException, MALException, ElementNotFoundException {
		MALCategoryGuidelineConfig categoryGuideLineConfig = getCategoryGuideLineConfig(component);
		SortEnum sortEnum = categoryGuideLineConfig == null ? null : categoryGuideLineConfig.getSortOrder();
        if (sortEnum == null){
            return null;
        }
        switch (sortEnum.getType()) {
            case SortEnum.ASCENDING_TYPE:
                return new DataColumnComparator(true);
            case SortEnum.DESCENDING_TYPE:
                return new DataColumnComparator(false);
            case SortEnum.UNSORTED_TYPE:
                return null;
            default:
                return null;
        }
    }

    /**
     * @deprecated
     * @param component
     * @return
     */
    private static MALCategoryGuidelineConfig getCategoryGuideLineConfig(MALComponent component) {
        MALCategoryGuidelineConfig categoryGuideLineConfig = null;
        if ((component.getDefinitionType().equals("ChartComponent") == true) || (component.getDefinitionType().equals("TrendChartComponent") == true)) {
            categoryGuideLineConfig = ((MALChartComponent) component).getCategoryGuidelineConfig();
        } else if (component.getDefinitionType().equals("TextComponent") == true) {
            MALTextComponent textComponent = (MALTextComponent) component;
            categoryGuideLineConfig = ((MALTextVisualization) textComponent.getVisualization(0)).getCategoryGuidelineConfig();
        }
        return categoryGuideLineConfig;
    }

    /**
     * @deprecated
     * @param dataRows
     * @return
     */
    static DataColumn[] getAllHeaderDataColumns(DataRow[] dataRows) {
        if (dataRows == null) return null;
        if (dataRows.length == 0) return null;
        if (dataRows.length > 1) throw new IllegalArgumentException("Wrong usage of this API. The datarow passed should not contain more than one category.");
        return dataRows[0].getDataColumn();
    }

    static DataRow[] convertToSingleItemDataRowArray(DataColumn[] dataColumns) {
        DataRow[] dataRows = new DataRow[]{new DataRow()};
        dataRows[0].setDataColumn(dataColumns);
        return dataRows;
    }

}