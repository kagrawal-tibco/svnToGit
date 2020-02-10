package com.tibco.cep.studio.dashboard.core.insight.model.configs;

import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalParticle;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class LocalPieChartVisualization extends LocalVisualization {
	
	public LocalPieChartVisualization() {
		super(BEViewsElementNames.PIE_CHART_VISUALIZATION);
	}

	public LocalPieChartVisualization(LocalElement parentElement, BEViewsElement mdElement) {
		super(parentElement, BEViewsElementNames.PIE_CHART_VISUALIZATION, mdElement);
	}

	public LocalPieChartVisualization(LocalElement parentConfig, String name) {
		super(parentConfig, BEViewsElementNames.PIE_CHART_VISUALIZATION, name);
	}
	
	@Override
	protected void validateParticle(LocalParticle particle) throws Exception {
		super.validateParticle(particle);
		if (BEViewsElementNames.CHART_SERIES_CONFIG.equals(particle.getName()) == true) {
			if (particle.getActiveElementCount() > 1){
				//we have more than one series config, log a warning 
				addValidationInfoMessage("A pie chart should not have more than one series");
			}
		}
	}

}
