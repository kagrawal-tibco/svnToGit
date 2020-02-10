package com.tibco.cep.dashboard.plugin.beviews.vizengine;

import java.util.LinkedList;
import java.util.List;

import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.mal.model.MALBarChartVisualization;
import com.tibco.cep.dashboard.psvr.mal.model.MALChartComponentColorSet;
import com.tibco.cep.dashboard.psvr.mal.model.MALComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALVisualization;
import com.tibco.cep.dashboard.psvr.mal.model.types.OrientationEnum;
import com.tibco.cep.dashboard.psvr.ogl.model.TypeSpecificAttribute;
import com.tibco.cep.dashboard.psvr.ogl.model.types.ChartRenderingType;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;

/**
 * @author apatil
 *
 */
public class BarChartVisualizationHandler extends ChartVisualizationHandler {

	protected ChartRenderingType getChartRenderingType(MALVisualization visualization) {
		MALBarChartVisualization barChartVisualization = (MALBarChartVisualization) visualization;
		if (barChartVisualization.getOrientation().getType() == OrientationEnum.HORIZONTAL_TYPE) {
			return ChartRenderingType.HORIZONTALBAR;
		}
		return ChartRenderingType.VERTICALBAR;
	}

	protected TypeSpecificAttribute[] getTypeSpecificAttributes(MALComponent component, MALVisualization visualization, PresentationContext ctx) throws MALException {
		MALBarChartVisualization barChartVisualization = (MALBarChartVisualization) visualization;
		List<TypeSpecificAttribute> attributes = new LinkedList<TypeSpecificAttribute>();
		// width
		String attrValue = Integer.toString(barChartVisualization.getWidth());
		attributes.add(createTypeSpecificAttribute("width", attrValue));
		// topCapThickness
		attrValue = Double.toString(barChartVisualization.getTopCapThickness());
		attributes.add(createTypeSpecificAttribute("topcapthickness", attrValue));
		// check if topCapThickness is more then 0, if so then make topcap true
		if (barChartVisualization.getTopCapThickness() > 0) {
			attributes.add(createTypeSpecificAttribute("topcap", "true"));
		}
		// topCapColor
		MALChartComponentColorSet componentColorSet = (MALChartComponentColorSet) ctx.getViewsConfigHelper().getComponentColorSet(component, component.getComponentColorSetIndex());
		attrValue = componentColorSet.getTopCapColor();
		attributes.add(createTypeSpecificAttribute("topcapcolor", attrValue));
		// overlappercentage
		attrValue = Integer.toString(barChartVisualization.getOverlapPercentage());
		attributes.add(createTypeSpecificAttribute("overlappercentage", attrValue));

		return attributes.toArray(new TypeSpecificAttribute[attributes.size()]);
	}

}
