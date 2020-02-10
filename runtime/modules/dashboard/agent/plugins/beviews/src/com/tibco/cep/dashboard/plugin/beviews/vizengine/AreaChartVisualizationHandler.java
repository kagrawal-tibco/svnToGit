package com.tibco.cep.dashboard.plugin.beviews.vizengine;

import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.mal.model.MALAreaChartVisualization;
import com.tibco.cep.dashboard.psvr.mal.model.MALComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALVisualization;
import com.tibco.cep.dashboard.psvr.ogl.model.TypeSpecificAttribute;
import com.tibco.cep.dashboard.psvr.ogl.model.types.ChartRenderingType;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;

/**
 * @author apatil
 *
 */
public class AreaChartVisualizationHandler extends LineChartVisualizationHandler {

	protected ChartRenderingType getChartRenderingType(MALVisualization visualization) {
		return ChartRenderingType.AREA;
	}

	protected TypeSpecificAttribute[] getTypeSpecificAttributes(MALComponent component, MALVisualization visualization, PresentationContext ctx) throws MALException {
		TypeSpecificAttribute[] lineAttrs = super.getTypeSpecificAttributes(component, visualization, ctx);
		TypeSpecificAttribute[] areaAttrs = new TypeSpecificAttribute[lineAttrs.length + 1];
		System.arraycopy(lineAttrs, 0, areaAttrs, 0, lineAttrs.length);
		MALAreaChartVisualization areaChartVisualization = (MALAreaChartVisualization) visualization;
		areaAttrs[lineAttrs.length] = createTypeSpecificAttribute("fillopacity", Integer.toString(areaChartVisualization.getFillOpacity()));
		return areaAttrs;
	}
}
