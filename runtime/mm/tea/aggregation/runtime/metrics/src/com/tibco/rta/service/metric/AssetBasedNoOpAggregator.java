package com.tibco.rta.service.metric;

import com.tibco.rta.Fact;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.model.DimensionHierarchy;

/**
 * 
 * 
 *
 */
public class AssetBasedNoOpAggregator extends AssetBasedAggregator {

	private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_SERVICES_METRIC.getCategory());

    public AssetBasedNoOpAggregator (Fact fact, DimensionHierarchy hierarchy) throws Exception {
    	super(fact, hierarchy);
    }


    public void computeMetric() throws Exception {
    	//no-op
    }


	@Override
	public void initAggregator() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected boolean preProcess() throws Exception {
		return true;
	}
	
	@Override
	protected void postProcess() {
		
	}
}
