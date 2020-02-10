package com.tibco.cep.bemm.monitoring.metric.collector.impl;

import java.util.Map;

import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.monitoring.metric.collector.MetricsCollectorCallback;
import com.tibco.rta.Fact;

public class MetricsCollectorFactsPublishCallback implements MetricsCollectorCallback {

	@Override
	public void publish(Map<String, Object> factAttributes) {
		try {
			Fact fact = BEMMServiceProviderManager.getInstance().getAggregationService().createFact();
	        fact.setAttributes(factAttributes);
			BEMMServiceProviderManager.getInstance().getAggregationService().getSession().publishFact(fact);
		} catch (Exception ex) {
			//TODO handle exception
		}
	}

}
