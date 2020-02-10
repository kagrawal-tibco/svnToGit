package com.tibco.cep.dashboard.plugin.internal.runtime;

import com.tibco.cep.dashboard.psvr.common.NonFatalException;
import com.tibco.cep.dashboard.psvr.mal.model.MALFlowLayoutConstraint;
import com.tibco.cep.dashboard.psvr.mal.model.MALLayout;
import com.tibco.cep.dashboard.psvr.mal.model.MALLayoutConstraint;
import com.tibco.cep.dashboard.psvr.ogl.model.LayoutConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.LayoutConstraints;
import com.tibco.cep.dashboard.psvr.vizengine.LayoutConfigGenerator;

public class FlowLayoutGenerator extends LayoutConfigGenerator {

	@Override
	protected void init() {
		
	}

	@Override
	protected void shutdown() throws NonFatalException {
		
	}

	@Override
	public LayoutConfig generate(MALLayout layout) {
        LayoutConfig layoutConfig = new LayoutConfig();
        layoutConfig.setType("flow");
        layoutConfig.setHeight(layout.getComponentHeight());
        layoutConfig.setWidth(layout.getComponentWidth());
        //TODO layoutConfig.setMovieURL("MetricPanel.swf") should be removed when we move from Insight to BEViews 
        layoutConfig.setMovieURL("MetricPanel.swf");
        return layoutConfig;
	}

	@Override
	public LayoutConstraints getLayoutConstraints(MALLayoutConstraint malLayoutConstraint) {
        MALFlowLayoutConstraint flowLayoutConstraints = (MALFlowLayoutConstraint) malLayoutConstraint;
        LayoutConstraints constraints = new LayoutConstraints();
        constraints.setColSpan(flowLayoutConstraints.getComponentColSpan());
        constraints.setRowSpan(flowLayoutConstraints.getComponentRowSpan());
        //PATCH add repositioning to constraints in OGL.xsd
        return constraints;		
		
	}
	
}