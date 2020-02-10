package com.tibco.cep.bemm.monitoring.metric.view;

import java.util.HashMap;
import java.util.Map;

import com.tibco.cep.bemm.monitoring.metric.view.config.MetricsView;

public class ViewData {
	
	MetricsView view=null;
	
	Map<String, Object> queryData=new HashMap<String, Object>();

	public MetricsView getView() {
		return view;
	}

	public void setView(MetricsView chart) {
		this.view = chart;
	}

	public Map<String, Object> getQueryData() {
		return queryData;
	}

	public void setQueryData(Map<String, Object> queryData) {
		this.queryData = queryData;
	}
	
	
	
}
