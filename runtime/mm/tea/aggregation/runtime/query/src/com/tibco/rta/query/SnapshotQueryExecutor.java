package com.tibco.rta.query;

import java.util.ArrayList;
import java.util.List;

import com.tibco.rta.common.service.ServiceProviderManager;
import com.tibco.rta.runtime.model.MetricNode;
import com.tibco.rta.service.persistence.PersistenceService;

public class SnapshotQueryExecutor {
	
	QueryDef queryDef;
	PersistenceService pServ;
	Browser<MetricNode> browser;
	
	public SnapshotQueryExecutor (QueryDef queryDef) throws Exception {
		pServ = ServiceProviderManager.getInstance().getPersistenceService();
		this.queryDef = queryDef;
		this.browser = pServ.getSnapshotMetricNodeBrowser(queryDef);
	}
	
	
	public List<MetricNode> getNextBatch() throws Exception {
		List<MetricNode> metricNodeBatch = new ArrayList<MetricNode>();
		for (int i=0; i<queryDef.getBatchSize(); i++) {
			if (browser.hasNext()) {
				metricNodeBatch.add(browser.next());
			} else {
				break;
			}
		}
		return metricNodeBatch;
		
	}
	
	public void stop() {
		browser.stop();
	}
	

}
