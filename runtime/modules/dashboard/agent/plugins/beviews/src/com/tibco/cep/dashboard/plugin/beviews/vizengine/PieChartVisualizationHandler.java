package com.tibco.cep.dashboard.plugin.beviews.vizengine;

import java.util.List;

import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.mal.model.MALChartComponentColorSet;
import com.tibco.cep.dashboard.psvr.mal.model.MALComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALPieChartVisualization;
import com.tibco.cep.dashboard.psvr.mal.model.MALSeriesColor;
import com.tibco.cep.dashboard.psvr.mal.model.MALSeriesConfig;
import com.tibco.cep.dashboard.psvr.mal.model.MALVisualization;
import com.tibco.cep.dashboard.psvr.ogl.model.TypeSpecificAttribute;
import com.tibco.cep.dashboard.psvr.ogl.model.types.ChartRenderingType;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.dashboard.psvr.vizengine.VisualizationException;

/**
 * @author apatil
 *
 */
public class PieChartVisualizationHandler extends ChartVisualizationHandler {

	public MALSeriesConfig[] getSeriesConfigs(MALVisualization visualization, List<String> seriesConfigNames) throws VisualizationException {
		if (visualization.getSeriesConfigCount() == 0) {
			throw new VisualizationException(visualization + " has no series configs");
		}
		if (visualization.getSeriesConfigCount() == 1) {
			return visualization.getSeriesConfig();
		}
		return new MALSeriesConfig[] { visualization.getSeriesConfig(0) };
	}

	protected ChartRenderingType getChartRenderingType(MALVisualization visualization) {
		return ChartRenderingType.PIE;
	}

	protected TypeSpecificAttribute[] getTypeSpecificAttributes(MALComponent component, MALVisualization visualization, PresentationContext ctx) {
		MALPieChartVisualization pieChartVisualization = (MALPieChartVisualization) visualization;
		TypeSpecificAttribute[] attrs = new TypeSpecificAttribute[3];
		// startingangle
		String attrValue = Integer.toString(pieChartVisualization.getStartingAngle());
		attrs[0] = createTypeSpecificAttribute("startingangle", attrValue);
		// direction
		attrValue = pieChartVisualization.getDirection().toString();
		attrs[1] = createTypeSpecificAttribute("direction", attrValue);
		// sector
		attrValue = Integer.toString(pieChartVisualization.getSector());
		attrs[2] = createTypeSpecificAttribute("sectors", attrValue);

		return attrs;
	}

	protected String getDefaultBaseColor(MALComponent component, MALVisualization visualization, PresentationContext ctx, int increment) throws MALException {
		MALSeriesColor seriesColor = getSeriesColor(component, visualization, ctx, increment);
		return seriesColor.getBaseColor();
	}

	/**
	 * @param component
	 * @param visualization
	 * @param ctx
	 * @param increment
	 * @return
	 * @throws MALException
	 */
	private MALSeriesColor getSeriesColor(MALComponent component, MALVisualization visualization, PresentationContext ctx, int increment) throws MALException {
		MALChartComponentColorSet colorSet = (MALChartComponentColorSet) ctx.getViewsConfigHelper().getComponentColorSet(component, component.getComponentColorSetIndex());
		int index = visualization.getSeriesColorIndex();
		if (index == -1) {
			index = component.getSeriesColorIndex();
			if (index == -1) {
				index = 0;
			}
		}
		index = index + increment;
		if (index >= colorSet.getSeriesColorCount()) {
			index = 0;
		}
		MALSeriesColor seriesColor = colorSet.getSeriesColor(index);
		return seriesColor;
	}

	protected String getDefaultHighlightColor(MALComponent component, MALVisualization visualization, PresentationContext ctx, int increment) throws MALException {
		MALSeriesColor seriesColor = getSeriesColor(component, visualization, ctx, increment);
		return seriesColor.getHighlightColor();
	}
}
