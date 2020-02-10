package com.tibco.cep.dashboard.plugin.beviews.vizengine;

import java.util.LinkedList;
import java.util.List;

import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.mal.model.MALComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALLineChartVisualization;
import com.tibco.cep.dashboard.psvr.mal.model.MALVisualization;
import com.tibco.cep.dashboard.psvr.mal.model.types.DataPlottingEnum;
import com.tibco.cep.dashboard.psvr.ogl.model.TypeSpecificAttribute;
import com.tibco.cep.dashboard.psvr.ogl.model.types.ChartRenderingType;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;

/**
 * @author apatil
 *
 */
public class LineChartVisualizationHandler extends ChartVisualizationHandler {

	protected ChartRenderingType getChartRenderingType(MALVisualization visualization) {
		return ChartRenderingType.LINE;
	}

	protected TypeSpecificAttribute[] getTypeSpecificAttributes(MALComponent component, MALVisualization visualization, PresentationContext ctx) throws MALException {
		MALLineChartVisualization lineChartViz = (MALLineChartVisualization) visualization;
		List<TypeSpecificAttribute> attrs = new LinkedList<TypeSpecificAttribute>();
		// linethickness
		String attrValue = Integer.toString(lineChartViz.getLineThickness());
		attrs.add(createTypeSpecificAttribute("linethickness", attrValue));
		// showdatapoints
		DataPlottingEnum dataPlotting = lineChartViz.getDataPlotting();
		if (dataPlotting != null) {
			attrs.add(createTypeSpecificAttribute("showdatapoint", dataPlotting.toString()));
		}
		// plotshape
		attrValue = lineChartViz.getPlotShape().toString();
		attrs.add(createTypeSpecificAttribute("plotshape", attrValue));
		// plotshapedimension
		attrValue = Integer.toString(lineChartViz.getPlotShapeDimension());
		attrs.add(createTypeSpecificAttribute("plotshapedimension", attrValue));

		return attrs.toArray(new TypeSpecificAttribute[attrs.size()]);
	}
}