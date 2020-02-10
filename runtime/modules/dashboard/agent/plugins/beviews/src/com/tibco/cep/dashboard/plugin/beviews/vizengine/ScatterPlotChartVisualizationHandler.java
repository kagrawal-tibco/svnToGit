package com.tibco.cep.dashboard.plugin.beviews.vizengine;

import java.util.LinkedList;
import java.util.List;

import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.mal.model.MALComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALScatterPlotChartVisualization;
import com.tibco.cep.dashboard.psvr.mal.model.MALVisualization;
import com.tibco.cep.dashboard.psvr.ogl.model.TypeSpecificAttribute;
import com.tibco.cep.dashboard.psvr.ogl.model.types.ChartRenderingType;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;

public class ScatterPlotChartVisualizationHandler extends ChartVisualizationHandler {

    @Override
    protected ChartRenderingType getChartRenderingType(MALVisualization visualization) {
        return ChartRenderingType.SCATTER;
    }

    @Override
    protected TypeSpecificAttribute[] getTypeSpecificAttributes(MALComponent component, MALVisualization visualization, PresentationContext ctx) throws MALException {
        MALScatterPlotChartVisualization scatterPlotViz = (MALScatterPlotChartVisualization) visualization;
        List<TypeSpecificAttribute> attrs = new LinkedList<TypeSpecificAttribute>();
        //linethickness
        //plotshape
        String attrValue = scatterPlotViz.getPlotShape().toString();
        attrs.add(createTypeSpecificAttribute("plotshape", attrValue));
        //plotshapedimension
        attrValue = Integer.toString(scatterPlotViz.getPlotShapeDimension());
        attrs.add(createTypeSpecificAttribute("plotshapedimension", attrValue));  
        
        return attrs.toArray(new TypeSpecificAttribute[attrs.size()]);
    }

}
